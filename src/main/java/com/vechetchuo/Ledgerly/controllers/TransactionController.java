package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.transaction.*;
import com.vechetchuo.Ledgerly.services.TransactionService;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.PaginationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transaction", description = "the Transaction Api")
@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/v1/transaction/get-transaction")
    public ApiResponse<GetTransactionResponse> getTransaction(@Valid @RequestBody GetTransactionRequest req){
        return transactionService.getTransaction(req);
    }

    @PostMapping("/v1/transaction/get-transactions")
    public ApiResponse<GetTransactionsResponse> getTransactions(@Valid @RequestBody PaginationRequest req){
        return transactionService.getTransactions(req);
    }

    @PostMapping("/v1/transaction/create-transaction")
    public ApiResponse<CreateTransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest req){
        return transactionService.createTransaction(req);
    }

    @PostMapping("/v1/transaction/update-transaction")
    public ApiResponse<UpdateTransactionResponse> updateTransaction(@Valid @RequestBody UpdateTransactionRequest req){
        return transactionService.updateTransaction(req);
    }

    @PostMapping("/v1/transaction/delete-transaction")
    public ApiResponse<DeleteTransactionResponse> deleteTransaction(@Valid @RequestBody DeleteTransactionRequest req){
        return transactionService.deleteTransaction(req);
    }
}
