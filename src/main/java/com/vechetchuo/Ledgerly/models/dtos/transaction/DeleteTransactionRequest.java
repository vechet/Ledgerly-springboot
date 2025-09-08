package com.vechetchuo.Ledgerly.models.dtos.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTransactionRequest {
    @NotNull(message = "This field Id is required!")
    private Integer id;
}
