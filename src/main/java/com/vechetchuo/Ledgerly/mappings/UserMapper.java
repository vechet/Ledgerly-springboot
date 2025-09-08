package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.User;
import com.vechetchuo.Ledgerly.models.dtos.auth.RecordAuditLogAuth;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterRequest;
import com.vechetchuo.Ledgerly.models.dtos.auth.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    User toCreateEntity(RegisterRequest dto);

    RegisterResponse toCreateDto(User entity);

    RecordAuditLogAuth toAuditLogDto(User entity);
}
