package com.vechetchuo.Ledgerly.models.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountResponse {
    private int id;
    private String name;
    private String currency;
    private String memo;
}
