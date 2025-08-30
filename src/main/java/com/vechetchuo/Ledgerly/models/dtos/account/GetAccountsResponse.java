package com.vechetchuo.Ledgerly.models.dtos.account;

import com.vechetchuo.Ledgerly.utils.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountsResponse {
    private List<AccountsResponse> accounts;
    private PageInfo pageInfo;
}
