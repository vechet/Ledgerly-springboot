package com.vechetchuo.Ledgerly.models.dtos.auth;

import com.vechetchuo.Ledgerly.validations.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    private String token;
    @ValidPassword
    private String newPassword;
}
