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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleSeeder {

    @Autowired private RoleRepository roleRepository;
    @Autowired private RoleClaimRepository roleClaimRepository;

    private final Map<EnumRoles, EnumPermissions[]> rolePermissionMap = Map.of(
            EnumRoles.ROLE_SYSTEM_ADMIN, EnumPermissions.values(),
            EnumRoles.ROLE_ADMIN, new EnumPermissions[] {
                    EnumPermissions.ACCOUNT_VIEW,
                    EnumPermissions.ACCOUNT_UPDATE,
                    EnumPermissions.CATEGORY_VIEW,
                    EnumPermissions.TRANSACTION_VIEW
            },
            EnumRoles.ROLE_USER, EnumPermissions.values()
    );

    @Transactional
    public void seed() {
        String claimType = EnumClaimTypes.PERMISSIONS.toString();

        for (var entry : rolePermissionMap.entrySet()) {
            String roleName = entry.getKey().name();
            var permissions = entry.getValue();

            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(roleName)));

            Set<String> existingClaims = roleClaimRepository.findByRoleIdAndClaimType(role.getId(), claimType)
                    .stream().map(RoleClaim::getClaimValue).collect(Collectors.toSet());

            for (EnumPermissions perm : permissions) {
                String value = perm.name();
                if (!existingClaims.contains(value)) {
                    roleClaimRepository.save(new RoleClaim(role, claimType, value));
                }
            }
        }
    }
}
