package com.vechetchuo.Ledgerly.repositories;

import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.domains.GlobalParam;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE " +
    "(:name IS NULL OR :name = '' OR a.name = :name) AND " +
    "(:userId IS NULL OR :userId = '' OR a.userId = :userId) AND " +
    "(:globalParam IS NULL OR a.globalParam = :globalParam)")
    Page<Account> findDynamic(@Param("name") String name, @Param("userId") String userId, @Param("globalParam") GlobalParam globalParam, Pageable pageable);
}
