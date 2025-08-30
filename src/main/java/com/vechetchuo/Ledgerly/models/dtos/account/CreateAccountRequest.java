package com.vechetchuo.Ledgerly.models.dtos.account;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotNull(message = "This field Name is required!")
    private String name;
    @NotNull(message = "This field Currency is required!")
    private String currency;
    private String memo;
}
