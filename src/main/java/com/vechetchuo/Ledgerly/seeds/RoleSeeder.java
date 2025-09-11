package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.enums.EnumClaimTypes;
import com.vechetchuo.Ledgerly.enums.EnumPermissions;
import com.vechetchuo.Ledgerly.enums.EnumRoles;
import com.vechetchuo.Ledgerly.models.domains.Role;
import com.vechetchuo.Ledgerly.models.domains.RoleClaim;
import com.vechetchuo.Ledgerly.repositories.RoleClaimRepository;
import com.vechetchuo.Ledgerly.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoleSeeder {

    @Autowired private RoleRepository roleRepository;
    @Autowired private RoleClaimRepository roleClaimRepository;

    public void seed() {
        // init roles and permissions
        Map<EnumRoles, List<EnumPermissions>> rolePermissions = new HashMap<>();
        rolePermissions.put(EnumRoles.ROLE_SYSTEM_ADMIN, Arrays.asList(EnumPermissions.values()));
        rolePermissions.put(EnumRoles.ROLE_ADMIN, Arrays.asList(
                EnumPermissions.ACCOUNT_VIEW,
                EnumPermissions.ACCOUNT_UPDATE,
                EnumPermissions.CATEGORY_VIEW,
                EnumPermissions.TRANSACTION_VIEW
        ));
        rolePermissions.put(EnumRoles.ROLE_USER, Arrays.asList(EnumPermissions.values()));

        // claim type permissions
        String claimType = EnumClaimTypes.PERMISSIONS.toString();

        // process init role and permission
        rolePermissions.forEach((role, permissions) -> {
            var roleName = role.getMessage();

            Role newRole = roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(roleName)));

            Set<String> existingClaims = roleClaimRepository.findByRoleIdAndClaimType(newRole.getId(), claimType)
                    .stream().map(RoleClaim::getClaimValue).collect(Collectors.toSet());

            for (EnumPermissions permission : permissions){
                var permName = permission.getMessage();
                if (!existingClaims.contains(permName)) {
                    roleClaimRepository.save(new RoleClaim(newRole, claimType, permName));
                }
            }
        });
    }
}
