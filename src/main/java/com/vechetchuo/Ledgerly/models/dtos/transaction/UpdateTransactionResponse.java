package com.vechetchuo.Ledgerly.models.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransactionResponse {
    private int id;
    private Integer accountId;
    private Integer categoryId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String memo;
    private String type;
}
