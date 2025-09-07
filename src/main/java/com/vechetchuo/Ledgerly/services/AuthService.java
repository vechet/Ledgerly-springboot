package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.mappings.UserMapper;
import com.vechetchuo.Ledgerly.models.domains.AuditLog;
import com.vechetchuo.Ledgerly.models.domains.Role;
import com.vechetchuo.Ledgerly.models.domains.RoleClaim;
import com.vechetchuo.Ledgerly.models.domains.UserRole;
import com.vechetchuo.Ledgerly.models.dtos.auth.*;
import com.vechetchuo.Ledgerly.repositories.AuditLogRepository;
import com.vechetchuo.Ledgerly.repositories.RoleRepository;
import com.vechetchuo.Ledgerly.repositories.UserRepository;
import com.vechetchuo.Ledgerly.repositories.UserRoleRepository;
import com.vechetchuo.Ledgerly.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuditLogRepository auditLogRepository;
    @Autowired private UserMapper mapper;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;

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

            var userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setRoles(user.getUserRoles().stream()
                    .map(UserRole::getRole)              // go from UserRole to Role
                    .map(Role::getName)                  // get role name
                    .distinct()
                    .toList());
            userResponse.setPermissions(user.getUserRoles().stream()
                    .map(UserRole::getRole)              // go from UserRole to Role
                    .flatMap(role -> role.getRoleClaims().stream()) // get RoleClaims
                    .map(RoleClaim::getClaimValue)       // get permission string
                    .distinct()
                    .toList());

            res.setUser(userResponse);
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

    public String GetAuditDescription(int id){
        var user = userRepository.findById(id).orElse(null);
        var recordAuditLogUser = mapper.toAuditLogDto(user);
        return JsonConverterUtils.SerializeObject(recordAuditLogUser);
    }
}
