package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.mappings.UserMapper;
import com.vechetchuo.Ledgerly.models.domains.AuditLog;
import com.vechetchuo.Ledgerly.models.domains.Role;
import com.vechetchuo.Ledgerly.models.domains.UserRole;
import com.vechetchuo.Ledgerly.models.dtos.auth.LoginRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.LoginResponse;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterResponse;
import com.vechetchuo.Ledgerly.repositories.AuditLogRepository;
import com.vechetchuo.Ledgerly.repositories.RoleRepository;
import com.vechetchuo.Ledgerly.repositories.UserRepository;
import com.vechetchuo.Ledgerly.repositories.UserRoleRepository;
import com.vechetchuo.Ledgerly.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired private UserRoleRepository userRoleRepository;

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

    public ApiResponse<LoginResponse> login(LoginRequest req){
        try{
            // get user
            var user = userRepository.findByUsername(req.getUsername()).orElse(null);

            // check if user not exists
            if (user == null) {
                logger.info(LoggerUtil.formatMessage(req, ApiResponseStatus.NOT_FOUND));
                return ApiResponse.failure(ApiResponseStatus.NOT_FOUND);
            }

            // generate token
            var token = jwtUtil.generateToken(user);

            // Response
            var res = new LoginResponse(token);
            return ApiResponse.success(res);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }

    public String GetAuditDescription(int id){
        var user = userRepository.findById(id).orElse(null);
        var recordAuditLogUser = mapper.toAuditLogDto(user);
        return JsonConverterUtils.SerializeObject(recordAuditLogUser);
    }
}
