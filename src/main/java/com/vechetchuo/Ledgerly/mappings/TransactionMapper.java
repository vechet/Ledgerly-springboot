package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Transaction;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.models.dtos.transaction.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    GetTransactionResponse toGetDto(Transaction entity);

    TransactionsResponse toGetsDto(Transaction entity);

    Transaction toCreateEntity(CreateTransactionRequest dto);
    CreateTransactionResponse toCreateDto(Transaction entity);

    Transaction toUpdateEntity(UpdateTransactionRequest dto);
    UpdateTransactionResponse toUpdateDto(Transaction entity);

    Transaction toDeleteEntity(DeleteTransactionRequest dto);
    DeleteTransactionResponse toDeleteDto(Transaction entity);

    RecordAuditLogTransaction toAuditLogDto(Transaction entity);
}
