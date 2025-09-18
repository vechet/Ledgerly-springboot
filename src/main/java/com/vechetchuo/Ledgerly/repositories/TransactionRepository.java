package com.vechetchuo.Ledgerly.repositories;

import com.vechetchuo.Ledgerly.models.domains.GlobalParam;
import com.vechetchuo.Ledgerly.models.domains.Transaction;
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
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE " +
    "(:memo IS NULL OR :memo = '' OR t.memo = :memo) AND " +
    "(:userId IS NULL OR :userId = '' OR t.userId = :userId) AND " +
    "(:globalParam IS NULL OR t.globalParam = :globalParam)")
    Page<Transaction> findDynamic(@Param("memo") String name, @Param("userId") String userId, @Param("globalParam") GlobalParam globalParam, Pageable pageable);

    List<Transaction> findByAccountId(int accountId);
}
