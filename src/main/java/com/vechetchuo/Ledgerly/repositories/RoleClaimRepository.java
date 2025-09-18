package com.vechetchuo.Ledgerly.repositories;

import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.domains.RoleClaim;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Hidden
@Repository
public interface RoleClaimRepository extends JpaRepository<RoleClaim, Integer> {
    List<RoleClaim> findByRoleIdAndClaimType(int roleId, String claimType);

}
