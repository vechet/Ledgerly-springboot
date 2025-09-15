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
    @Mapping(source = "systemValue", target = "defaultCategory")
    GetCategoryResponse toGetDto(Category entity);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "systemValue", target = "defaultCategory")
    CategoriesResponse toGetsDto(Category entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "iconName", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "systemValue", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Category toCreateEntity(CreateCategoryRequest dto);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "systemValue", target = "defaultCategory")
    CreateCategoryResponse toCreateDto(Category entity);

    @Mapping(target = "iconName", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "systemValue", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Category toUpdateEntity(UpdateCategoryRequest dto);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "systemValue", target = "defaultCategory")
    UpdateCategoryResponse toUpdateDto(Category entity);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "iconName", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "systemValue", ignore = true)
    @Mapping(target = "memo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "globalParam", ignore = true)
    Category toDeleteEntity(DeleteCategoryRequest dto);

    DeleteCategoryResponse toDeleteDto(Category entity);

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "systemValue", target = "defaultCategory")
    @Mapping(source = "globalParam.id", target = "statusId")
    @Mapping(source = "globalParam.name", target = "statusName")
    RecordAuditLogCategory toAuditLogDto(Category entity);
}
