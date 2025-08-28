package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountRequest;
import com.vechetchuo.Ledgerly.models.dtos.account.GetAccountResponse;
import com.vechetchuo.Ledgerly.services.AccountService;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Brand", description = "the Brand Api")
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/v1/account/get-account")
    public ApiResponse<GetAccountResponse> getAccount(@Valid @RequestBody GetAccountRequest req){
        return accountService.getAccount(req);
    }
}
