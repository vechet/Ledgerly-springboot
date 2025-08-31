package com.vechetchuo.Ledgerly.models.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionResponse {
    private int id;
    private Integer accountId;
    private String accountName;
    private Integer categoryId;
    private String categoryName;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String memo;
    private String type;
}
