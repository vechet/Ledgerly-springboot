package com.vechetchuo.Ledgerly.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String id;
    private String username;
    private String phone;
    private String email;
    private String address;
}
