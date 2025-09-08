package com.vechetchuo.Ledgerly.models.dtos.account;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountRequest {
    @NotNull(message = "This field Name is required!")
    private Integer id;
}
