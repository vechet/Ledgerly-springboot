package com.vechetchuo.Ledgerly.models.dtos.auth;

import com.vechetchuo.Ledgerly.validations.ValidPassword;
import jakarta.validation.constraints.Email;
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
    @ValidPassword
    private String password;
    private String phone;
    @Email(message = "Please provide a valid email address")
    private String email;
    private String address;
}
