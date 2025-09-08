package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    GetAccountResponse toGetDto(Account entity);

    AccountsResponse toGetsDto(Account entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Account toCreateEntity(CreateAccountRequest dto);

    CreateAccountResponse toCreateDto(Account entity);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Account toUpdateEntity(UpdateAccountRequest dto);

    UpdateAccountResponse toUpdateDto(Account entity);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "memo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Account toDeleteEntity(DeleteAccountRequest dto);

    DeleteAccountResponse toDeleteDto(Account entity);

    @Mapping(source = "globalParam.id", target = "statusId")
    @Mapping(source = "globalParam.name", target = "statusName")
    RecordAuditLogAccount toAuditLogDto(Account entity);
}
