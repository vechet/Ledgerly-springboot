package com.vechetchuo.Ledgerly.models.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "This field Username is required!")
    private String username;
    @NotBlank(message = "This field Password is required!")
    private String password;
    private String phone;
    private String email;
    private String address;
}
