package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.models.domains.Role;
import com.vechetchuo.Ledgerly.models.domains.User;
import com.vechetchuo.Ledgerly.models.domains.UserRole;
import com.vechetchuo.Ledgerly.repositories.RoleRepository;
import com.vechetchuo.Ledgerly.repositories.UserRepository;
import com.vechetchuo.Ledgerly.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserSeeder {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRoleRepository userRoleRepository;
//    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public User seedSystemAdmin() {
        return userRepository.findByUsername("ssadmin").orElseGet(() -> {
            Role role = roleRepository.findByName(EnumRoles.ROLE_SYSTEM_ADMIN.name())
                    .orElseThrow(() -> new RuntimeException("System admin role not found"));

            User user = new User();
            user.setUsername("ssadmin");
//            user.setPassword(passwordEncoder.encode("chet@12345"));
            user.setPassword("chet@12345");
            user.setEnabled(true);
            userRepository.save(user);

            userRoleRepository.save(new UserRole(user, role));
            return user;
        });
    }
}

