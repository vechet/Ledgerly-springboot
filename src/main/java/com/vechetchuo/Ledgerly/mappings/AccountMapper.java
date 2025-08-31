package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Account;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import org.mapstruct.Mapper;

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

    RecordAuditLogAccount toAuditLogDto(Account entity);
}
