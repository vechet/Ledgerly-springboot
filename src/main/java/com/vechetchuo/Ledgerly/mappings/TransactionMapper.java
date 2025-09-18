package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Transaction;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.models.dtos.transaction.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "account.currency", target = "currency")
    GetTransactionResponse toGetDto(Transaction entity);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "account.currency", target = "currency")
    TransactionsResponse toGetsDto(Transaction entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Transaction toCreateEntity(CreateTransactionRequest dto);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "account.currency", target = "currency")
    CreateTransactionResponse toCreateDto(Transaction entity);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Transaction toUpdateEntity(UpdateTransactionRequest dto);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "account.currency", target = "currency")
    UpdateTransactionResponse toUpdateDto(Transaction entity);

    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "transactionDate", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "memo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Transaction toDeleteEntity(DeleteTransactionRequest dto);

    DeleteTransactionResponse toDeleteDto(Transaction entity);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "globalParam.id", target = "statusId")
    @Mapping(source = "globalParam.name", target = "statusName")
    @Mapping(source = "account.currency", target = "currency")
    RecordAuditLogTransaction toAuditLogDto(Transaction entity);
}
