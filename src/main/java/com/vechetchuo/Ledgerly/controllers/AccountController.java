package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.services.AccountService;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Account", description = "the Account Api")
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/v1/account/get-account")
    public ApiResponse<GetAccountResponse> getAccount(@Valid @RequestBody GetAccountRequest req){
        return accountService.getAccount(req);
    }

    @PostMapping("/v1/account/get-accounts")
    public ApiResponse<GetAccountsResponse> getAccounts(@Valid @RequestBody GetAccountsRequest req){
        return accountService.getAccounts(req);
    }

    @PostMapping("/v1/account/create-account")
    public ApiResponse<CreateAccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest req){
        return accountService.createAccount(req);
    }

    @PostMapping("/v1/account/update-account")
    public ApiResponse<UpdateAccountResponse> updateAccount(@Valid @RequestBody UpdateAccountRequest req){
        return accountService.updateAccount(req);
    }

    @PostMapping("/v1/account/delete-account")
    public ApiResponse<DeleteAccountResponse> deleteAccount(@Valid @RequestBody DeleteAccountRequest req){
        return accountService.deleteAccount(req);
    }
}
