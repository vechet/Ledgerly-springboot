package com.vechetchuo.Ledgerly.models.dtos.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionResponse {
    private int id;
    private int accountId;
    private String accountName;
    private int categoryId;
    private String categoryName;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String memo;
    private String type;
}
