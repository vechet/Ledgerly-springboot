package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountRequest;
import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountResponse;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public ApiResponse<GetAccountResponse> getAccount(GetAccountRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        var result = new GetAccountResponse(req.getId(),"vechet", "USD", "");
        return ApiResponse.success(result);
    }
}
