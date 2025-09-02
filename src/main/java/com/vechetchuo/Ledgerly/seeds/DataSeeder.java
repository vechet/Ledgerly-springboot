package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.enums.*;
import com.vechetchuo.Ledgerly.models.domains.*;
import com.vechetchuo.Ledgerly.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GlobalParamRepository globalParamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleClaimRepository roleClaimRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public final Map<EnumRoles, EnumPermissions[]> rolePermissionMap = new EnumMap<>(EnumRoles.class);
    {
        rolePermissionMap.put(EnumRoles.ROLE_SYSTEM_ADMIN, EnumPermissions.values()); // full access
        rolePermissionMap.put(EnumRoles.ROLE_ADMIN, new EnumPermissions[] {
                EnumPermissions.ACCOUNT_VIEW,
                EnumPermissions.ACCOUNT_UPDATE,
                EnumPermissions.CATEGORY_VIEW,
                EnumPermissions.TRANSACTION_VIEW
        });
        rolePermissionMap.put(EnumRoles.ROLE_USER, EnumPermissions.values());
    }

    @Transactional
    @Override
    public void run(String... args) {
        String claimType = EnumClaimTypes.PERMISSIONS.toString();

        for (Map.Entry<EnumRoles, EnumPermissions[]> entry : rolePermissionMap.entrySet()) {
            String roleName = entry.getKey().name();
            EnumPermissions[] permissions = entry.getValue();

            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        var newRole = new Role();
                        newRole.setName(roleName);
                        roleRepository.save(newRole);
                        return newRole;
                    });

            Set<String> existingValues = roleClaimRepository.findByRoleIdAndClaimType(role.getId(), claimType)
                    .stream()
                    .map(RoleClaim::getClaimValue)
                    .collect(Collectors.toSet());

            for (EnumPermissions perm : permissions) {
                String value = perm.name();
                if (!existingValues.contains(value)) {
                    RoleClaim claim = new RoleClaim();
                    claim.setClaimType(claimType);
                    claim.setClaimValue(value);
                    claim.setRole(role);
                    roleClaimRepository.save(claim);
                }
            }
        }

        var getUser = userRepository.findByUsername("ssadmin").orElseGet(() -> {
            Role ssadminRole = roleRepository.findByName(EnumRoles.ROLE_SYSTEM_ADMIN.getMessage())
                    .orElseThrow(() -> new RuntimeException("ROLE_SYSTEM_ADMIN not found"));

            User newUser = new User();
            newUser.setUsername("ssadmin");
            newUser.setPassword("chet@12345");
            newUser.setEnabled(true);
            userRepository.save(newUser);

            UserRole userRole = new UserRole();
            userRole.setUser(newUser);
            userRole.setRole(ssadminRole);
            userRoleRepository.save(userRole);

            return newUser;
        });
        var userId = getUser.getId();

        if (globalParamRepository.count() == 0) {
            var globalParams = new ArrayList<GlobalParam>();

            for (var type : EnumGlobalParamType.values()) {
                var globalParamCategoryNormal = new GlobalParam();
                globalParamCategoryNormal.setName(EnumGlobalParam.Normal.getMessage());
                globalParamCategoryNormal.setKeyName(EnumGlobalParam.Normal.getMessage());
                globalParamCategoryNormal.setType(type.getMessage());
                globalParams.add(globalParamCategoryNormal);

                var globalParamCategoryDeleted = new GlobalParam();
                globalParamCategoryDeleted.setName(EnumGlobalParam.Deleted.getMessage());
                globalParamCategoryDeleted.setKeyName(EnumGlobalParam.Deleted.getMessage());
                globalParamCategoryDeleted.setType(type.getMessage());
                globalParams.add(globalParamCategoryDeleted);
            }

            globalParamRepository.saveAll(globalParams);
        }

        if (categoryRepository.count() == 0) {
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Normal.getMessage(), EnumGlobalParamType.CategoryxxxStatus.getMessage());
            var category = new Category();
            category.setName("Main");
            category.setUserId(String.valueOf(userId));
            category.setSystemValue(true);
            category.setGlobalParam(status);
            category.setCreatedBy(String.valueOf(userId));
            category.setCreatedDate(LocalDateTime.now());
            categoryRepository.save(category);
        }

    }
}

