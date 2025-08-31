package com.vechetchuo.Ledgerly.repositories;

import com.vechetchuo.Ledgerly.models.domains.GlobalParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalParamRepository extends JpaRepository<GlobalParam, Integer> {
    @Query("SELECT g FROM GlobalParam g WHERE g.keyName = :keyName and g.type = :type")
    GlobalParam findStatusByKeyNameAndType(@Param("keyName") String keyName, @Param("type") String type);

}
