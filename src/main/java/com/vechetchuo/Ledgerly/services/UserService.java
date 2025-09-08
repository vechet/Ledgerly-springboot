package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.models.dtos.auth.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public int getUserId(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }

    public String getUsername(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public boolean IsSystemAdminUser(){
        var user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return false;
    }
}
