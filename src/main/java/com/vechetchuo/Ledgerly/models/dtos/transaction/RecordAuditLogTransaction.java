package com.vechetchuo.Ledgerly.models.dtos.transaction;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordAuditLogTransaction {
    private int id;
    private String userId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private Integer accountId;
    private String accountName;
    private Integer categoryId;
    private String categoryName;
    private String memo;
    private String type;
    private int statusId;
    private String statusName;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
}
