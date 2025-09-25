package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.models.domains.Role;
import com.vechetchuo.Ledgerly.models.domains.User;
import com.vechetchuo.Ledgerly.models.domains.UserRole;
import com.vechetchuo.Ledgerly.repositories.RoleRepository;
import com.vechetchuo.Ledgerly.repositories.UserRepository;
import com.vechetchuo.Ledgerly.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserSeeder {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Value("${default_user}") private String default_user;
    @Value("${default_user_pass}") private String default_user_pass;
    @Value("${default_role}") private String default_role;

    public User seedSystemAdmin() {
        if (userRepository.count() > 0) return new User();

        return userRepository.findByUsername(default_user).orElseGet(() -> {
            Role role = roleRepository.findByName(default_role).orElse(null);

            User user = new User();
            user.setUsername(default_user);
            user.setPassword(passwordEncoder.encode(default_user_pass));
            user.setEnabled(true);
            userRepository.save(user);

            userRoleRepository.save(new UserRole(user, role));
            return user;
        });
    }
}

