package com.vechetchuo.Ledgerly.models.dtos.transaction;

import com.vechetchuo.Ledgerly.utils.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionsResponse {
    private List<TransactionsResponse> transactions;
    private PageInfo pageInfo;
}
