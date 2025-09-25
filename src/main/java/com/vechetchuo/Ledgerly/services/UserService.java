package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.models.dtos.auth.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public String getUserId(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }

    public String getUsername(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public String getEmail(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getEmail();
    }

    public String getPhone(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getPhone();
    }

    public String getAddress(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAddress();
    }

    public UserPrincipal getUserPrincipal(){
        return (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isSystemAdminUser() {
        var user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var requiredAuthority = new SimpleGrantedAuthority(EnumRoles.ROLE_SYSTEM_ADMIN.getMessage());
        return user.getAuthorities().contains(requiredAuthority);
    }

}
