package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.models.dtos.auth.LoginRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.LoginResponse;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterResponse;
import com.vechetchuo.Ledgerly.repositories.UserRepository;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.ApiResponseStatus;
import com.vechetchuo.Ledgerly.utils.JwtUtil;
import com.vechetchuo.Ledgerly.utils.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ApiResponse<RegisterResponse> register(RegisterRequest req){
        try{
           //

            return ApiResponse.success(null);
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
}
