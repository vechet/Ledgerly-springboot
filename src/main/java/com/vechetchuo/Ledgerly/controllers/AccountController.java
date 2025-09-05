package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.services.AccountService;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.PaginationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ACCOUNT_VIEW')")
    @PostMapping("/v1/account/get-account")
    public ApiResponse<GetAccountResponse> getAccount(@Valid @RequestBody GetAccountRequest req){
        return accountService.getAccount(req);
    }

    @PreAuthorize("hasAuthority('ACCOUNT_VIEW')")
    @PostMapping("/v1/account/get-accounts")
    public ApiResponse<GetAccountsResponse> getAccounts(@Valid @RequestBody PaginationRequest req){
        return accountService.getAccounts(req);
    }

    @PreAuthorize("hasAuthority('ACCOUNT_CREATE')")
    @PostMapping("/v1/account/create-account")
    public ApiResponse<CreateAccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest req){
        return accountService.createAccount(req);
    }

    @PreAuthorize("hasAuthority('ACCOUNT_UPDATE')")
    @PostMapping("/v1/account/update-account")
    public ApiResponse<UpdateAccountResponse> updateAccount(@Valid @RequestBody UpdateAccountRequest req){
        return accountService.updateAccount(req);
    }

    @PreAuthorize("hasAuthority('ACCOUNT_DELETE')")
    @PostMapping("/v1/account/delete-account")
    public ApiResponse<DeleteAccountResponse> deleteAccount(@Valid @RequestBody DeleteAccountRequest req){
        return accountService.deleteAccount(req);
    }
}
