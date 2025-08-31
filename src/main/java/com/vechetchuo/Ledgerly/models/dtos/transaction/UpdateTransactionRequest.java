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
public class UpdateTransactionRequest {
    @NotNull(message = "This field Name is required!")
    private int id;
    @NotNull(message = "This field Account Id is required!")
    private Integer accountId;
    @NotNull(message = "This field Category Id is required!")
    private Integer categoryId;
    @NotNull(message = "This field Amount Id is required!")
    private BigDecimal amount;
    @NotNull(message = "This field Transaction Date is required!")
    private LocalDateTime transactionDate;
    private String memo;
    @NotNull(message = "This field Type is required!")
    private String type;
}
