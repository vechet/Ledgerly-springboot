package com.vechetchuo.Ledgerly.models.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "This field Username is required!")
    private String username;
    @NotBlank(message = "This field Password is required!")
    private String password;
}
