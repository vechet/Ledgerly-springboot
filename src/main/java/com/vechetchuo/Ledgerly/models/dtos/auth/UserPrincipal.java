package com.vechetchuo.Ledgerly.models.dtos.auth;

import com.vechetchuo.Ledgerly.models.domains.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private final String userId;
    private final String username;
    private final String email;
    private final String phone;
    private final String address;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = authorities;
    }
}

