package com.vechetchuo.Ledgerly.services;

import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public ApiResponse<GetAccountResponse> getAccount(GetAccountRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        var result = new GetAccountResponse(req.getId(),"vechet", "USD", "");
        return ApiResponse.success(result);
    }

    public ApiResponse<GetAccountsResponse> getAccounts(GetAccountsRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        return ApiResponse.success(null);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse<CreateAccountResponse> createAccount(CreateAccountRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        return ApiResponse.success(null);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse<UpdateAccountResponse> updateAccount(UpdateAccountRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        return ApiResponse.success(null);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse<DeleteAccountResponse> deleteAccount(DeleteAccountRequest req){
        logger.info(LoggerUtils.formatMessage("Request", req));
        return ApiResponse.success(null);
    }
}
