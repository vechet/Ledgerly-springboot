package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Category;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.models.dtos.category.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    GetCategoryResponse toGetDto(Category entity);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    CategoriesResponse toGetsDto(Category entity);

    Category toCreateEntity(CreateCategoryRequest dto);
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    CreateCategoryResponse toCreateDto(Category entity);

    Category toUpdateEntity(UpdateCategoryRequest dto);
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    UpdateCategoryResponse toUpdateDto(Category entity);

    Category toDeleteEntity(DeleteCategoryRequest dto);
    DeleteCategoryResponse toDeleteDto(Category entity);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "globalParam.id", target = "statusId")
    @Mapping(source = "globalParam.name", target = "statusName")
    RecordAuditLogCategory toAuditLogDto(Category entity);
}
