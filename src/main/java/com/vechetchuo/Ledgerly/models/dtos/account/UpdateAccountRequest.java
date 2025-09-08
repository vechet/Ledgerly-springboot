package com.vechetchuo.Ledgerly.models.dtos.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequest {
    @NotNull(message = "This field Id is required!")
    private Integer id;
    @NotBlank(message = "This field Name is required!")
    private String name;
    @NotBlank(message = "This field Currency is required!")
    private String currency;
    private String memo;
}
