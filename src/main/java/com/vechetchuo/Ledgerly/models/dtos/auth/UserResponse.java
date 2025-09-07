package com.vechetchuo.Ledgerly.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String phone;
    private String email;
    private String address;
    private List<String> roles;
    private List<String> permissions;
}
