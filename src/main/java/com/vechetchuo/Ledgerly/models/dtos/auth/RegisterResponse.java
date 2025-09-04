package com.vechetchuo.Ledgerly.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
}
