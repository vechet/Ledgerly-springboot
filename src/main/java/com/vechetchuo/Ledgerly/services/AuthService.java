package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.models.dtos.auth.LoginRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.LoginResponse;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterResponse;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.ApiResponseStatus;
import com.vechetchuo.Ledgerly.utils.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

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
            //
            return ApiResponse.success(null);
        }catch (Exception e){
            logger.info(LoggerUtil.formatMessage(req, e.hashCode(), e.getMessage()));
            return ApiResponse.failure(ApiResponseStatus.INTERNAL_ERROR);
        }
    }
}
