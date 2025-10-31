package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.mappings.UserMapper;
import com.vechetchuo.Ledgerly.models.domains.*;
import com.vechetchuo.Ledgerly.models.dtos.auth.*;
import com.vechetchuo.Ledgerly.repositories.*;
import com.vechetchuo.Ledgerly.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Value("${reset.password.expiration}")
    private long resetPasswordExpiration;

    @Value("${reset.password.url}")
    private String resetPasswordUrl;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuditLogRepository auditLogRepository;
    @Autowired private UserMapper mapper;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserService userService;
    @Autowired private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired private EmailService emailService;

    @Transactional
    public ApiResponse<RegisterResponse> register(RegisterRequest req){
        try{
            // get user
            var user = userRepository.findByUsername(req.getUsername()).orElse(null);

            // check if duplicate username
            if (user != null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.DUPLICATION_USERNAME));
                return ApiResponse.failure(ApiResponseStatus.DUPLICATION_USERNAME);
            }

            // check if duplicate email
            if (userRepository.existsByEmail(req.getEmail())) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.DUPLICATION_EMAIL));
                return ApiResponse.failure(ApiResponseStatus.DUPLICATION_EMAIL);
            }

            // mapping dto to entity and add new user
            var newUser = mapper.toCreateEntity(req);
            newUser.setPassword(passwordEncoder.encode(req.getPassword()));
            newUser.setEnabled(true);
            userRepository.save(newUser);

            // assign user role
            Role role = roleRepository.findByName(EnumRoles.ROLE_USER.name())
                    .orElseThrow(() -> new RuntimeException("user role not found"));
            userRoleRepository.save(new UserRole(newUser, role));

            // Add audit log
            var accountAuditLog = new AuditLog();
            accountAuditLog.setControllerName("Account");
            accountAuditLog.setMethodName("Create");
            accountAuditLog.setTransactionId(newUser.getId());
            accountAuditLog.setTransactionNo(newUser.getUsername());
            accountAuditLog.setDescription(GetAuditDescription(newUser.getId()));
            accountAuditLog.setCreatedBy("1");
            accountAuditLog.setCreatedDate(LocalDateTime.now());
            auditLogRepository.save(accountAuditLog);

            // Response
            var res = mapper.toCreateDto(newUser);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public ApiResponse<LoginResponse> login(LoginRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            // Generate token using authenticated user
            var user = userRepository.findByUsername(req.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            var jwtToken = jwtUtil.generateToken(user);

            // Response
            var res = new LoginResponse();
            res.setToken(jwtToken.getToken());
            res.setExpiresIn(jwtToken.getExpiresIn());

            var userInfoResponse = new UserInfoResponse();
            userInfoResponse.setId(user.getId());
            userInfoResponse.setUsername(user.getUsername());
            userInfoResponse.setPhone(user.getPhone());
            userInfoResponse.setEmail(user.getEmail());
            userInfoResponse.setAddress(user.getAddress());
            userInfoResponse.setRoles(user.getUserRoles().stream()
                    .map(UserRole::getRole)              // go from UserRole to Role
                    .map(Role::getName)                  // get role name
                    .distinct()
                    .collect(Collectors.toList()));
            userInfoResponse.setPermissions(user.getUserRoles().stream()
                    .map(UserRole::getRole)              // go from UserRole to Role
                    .flatMap(role -> role.getRoleClaims().stream()) // get RoleClaims
                    .map(RoleClaim::getClaimValue)       // get permission string
                    .distinct()
                    .collect(Collectors.toList()));

            res.setUser(userInfoResponse);
            return ApiResponse.success(res);

        } catch (DisabledException ex) {
            logger.info("Login attempt for disabled user '{}'", req.getUsername());
            return ApiResponse.failure(ApiResponseStatus.UNAUTHORIZED, "User is disabled");
        } catch (LockedException ex) {
            logger.info("Login attempt for locked user '{}'", req.getUsername());
            return ApiResponse.failure(ApiResponseStatus.UNAUTHORIZED, "User account is locked");
        } catch (BadCredentialsException ex) {
            logger.info("Invalid login attempt for username '{}'", req.getUsername());
            return ApiResponse.failure(ApiResponseStatus.UNAUTHORIZED, "Invalid username or password");
        } catch (AuthenticationException ex) {
            logger.info("Authentication failed for username '{}'", req.getUsername());
            return ApiResponse.failure(ApiResponseStatus.UNAUTHORIZED, "Authentication failed");
        } catch (Exception ex) {
            logger.info("Unexpected error during login", ex);
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR, "Something went wrong");
        }
    }

    public String GetAuditDescription(String id){
        var user = userRepository.findById(id).orElse(null);
        var recordAuditLogUser = mapper.toAuditLogDto(user);
        return JsonConverterUtils.SerializeObject(recordAuditLogUser);
    }

    @Transactional
    public ApiResponse<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest req){
        try{
            User user = userRepository.findByEmail(req.getEmail()).orElse(null);
            if (user == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.EMAIL_NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.EMAIL_NOT_FOUND);
            }

            String token = UUID.randomUUID().toString();
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(resetPasswordExpiration);

            PasswordResetToken resetToken = new PasswordResetToken(token, expiry, user);
            passwordResetTokenRepository.save(resetToken);

            String link = resetPasswordUrl + token;
            emailService.sendResetLink(user.getEmail(), user.getUsername(), link);

            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<ResetPasswordResponse> resetPassword(ResetPasswordRequest req){
        try{
            PasswordResetToken token = passwordResetTokenRepository.findById(req.getToken()).orElse(null);
            if(token == null){
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.INVALID_LINK));
                return ApiResponse.failure(ApiResponseStatus.INVALID_LINK);
            }

            if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.LINK_EXPIRED));
                return ApiResponse.failure(ApiResponseStatus.LINK_EXPIRED);
            }

            User user = userRepository.findById(token.getUser().getId()).orElse(null);
            if (user == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.USER_NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.USER_NOT_FOUND);
            }

            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
            userRepository.save(user);

            passwordResetTokenRepository.delete(token); // Invalidate token

            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<ChangePasswordResponse> changePassword(ChangePasswordRequest req){
        try{
            String email = userService.getEmail(); // or username
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.EMAIL_NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.EMAIL_NOT_FOUND);
            }

            if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.CURRENT_PASSWORD_INCORRECT));
                return ApiResponse.failure(ApiResponseStatus.CURRENT_PASSWORD_INCORRECT);
            }

            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
            userRepository.save(user);

            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    @Transactional
    public ApiResponse<UserInfoResponse> userInfo(UserInfoRequest req){
        try{
            String username = userService.getUsername(); // or username
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.USER_NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.USER_NOT_FOUND);
            }

            var res = new UserInfoResponse();
            res.setId(user.getId());
            res.setUsername(user.getUsername());
            res.setPhone(user.getPhone());
            res.setEmail(user.getEmail());
            res.setAddress(user.getAddress());
            res.setRoles(user.getUserRoles().stream()
                    .map(UserRole::getRole)              // go from UserRole to Role
                    .map(Role::getName)                  // get role name
                    .distinct()
                    .collect(Collectors.toList()));
            res.setPermissions(user.getUserRoles().stream()
                    .map(UserRole::getRole)              // go from UserRole to Role
                    .flatMap(role -> role.getRoleClaims().stream()) // get RoleClaims
                    .map(RoleClaim::getClaimValue)       // get permission string
                    .distinct()
                    .collect(Collectors.toList()));

            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }
}
