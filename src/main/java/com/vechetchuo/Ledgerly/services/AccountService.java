package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountRequest;
import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountResponse;
import com.vechetchuo.Ledgerly.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public GetAccountResponse getAccount(GetAccountRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        return new GetAccountResponse(req.getId(),"vechet", "USD", "");
    }
}
