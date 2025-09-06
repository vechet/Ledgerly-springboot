package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.User;
import com.vechetchuo.Ledgerly.models.dtos.auth.RecordAuditLogAuth;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toCreateEntity(RegisterRequest dto);
    RegisterResponse toCreateDto(User entity);

//    @Mapping(source = "globalParam.id", target = "statusId")
//    @Mapping(source = "globalParam.name", target = "statusName")
    RecordAuditLogAuth toAuditLogDto(User entity);
}
