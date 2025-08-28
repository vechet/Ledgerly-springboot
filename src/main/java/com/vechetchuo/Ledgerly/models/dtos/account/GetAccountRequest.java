package com.vechetchuo.Ledgerly.models.dtos.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountRequest {
    @NotNull(message = "This field is required!")
    private Integer id;
}
