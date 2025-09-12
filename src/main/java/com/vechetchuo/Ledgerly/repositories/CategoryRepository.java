package com.vechetchuo.Ledgerly.repositories;

import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.domains.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE " +
    "(:name IS NULL OR :name = '' OR c.name = :name) AND " +
    "(:userId IS NULL OR :userId = '' OR c.userId = :userId)")
    Page<Category> findDynamic(@Param("name") String name, @Param("userId") String userId, Pageable pageable);
}
