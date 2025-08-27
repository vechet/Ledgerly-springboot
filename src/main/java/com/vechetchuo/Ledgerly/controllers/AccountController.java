package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountRequest;
import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountResponse;
import com.vechetchuo.Ledgerly.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/v1/account/get-account")
    public GetAccountResponse getAccount(@RequestBody GetAccountRequest req){
        return accountService.getAccount(req);
    }
}
