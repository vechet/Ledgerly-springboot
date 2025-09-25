package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.auth.*;
import com.vechetchuo.Ledgerly.services.AuthService;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "the auth api")
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired AuthService authService;

    @PostMapping("/v1/auth/register")
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest req){
        return authService.register(req);
    }

    @PostMapping("/v1/auth/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest req){
        return authService.login(req);
    }

    @PostMapping("/v1/auth/forgot-password")
    public ApiResponse<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req){
        return authService.forgotPassword(req);
    }

    @PostMapping("/v1/auth/reset-password")
    public ApiResponse<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest req){
        return authService.resetPassword(req);
    }
}
