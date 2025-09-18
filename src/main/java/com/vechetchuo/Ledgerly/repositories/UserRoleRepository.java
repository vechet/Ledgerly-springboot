package com.vechetchuo.Ledgerly.repositories;

import com.vechetchuo.Ledgerly.models.domains.UserRole;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
