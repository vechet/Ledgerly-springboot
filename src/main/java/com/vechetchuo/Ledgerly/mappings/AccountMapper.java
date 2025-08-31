package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    GetAccountResponse toGetDto(Account entity);

    AccountsResponse toGetsDto(Account entity);

    Account toCreateEntity(CreateAccountRequest dto);
    CreateAccountResponse toCreateDto(Account entity);

    Account toUpdateEntity(UpdateAccountRequest dto);
    UpdateAccountResponse toUpdateDto(Account entity);

    Account toDeleteEntity(DeleteAccountRequest dto);
    DeleteAccountResponse toDeleteDto(Account entity);

    @Mapping(source = "globalParam.id", target = "statusId")
    @Mapping(source = "globalParam.name", target = "statusName")
    RecordAuditLogAccount toAuditLogDto(Account entity);
}
