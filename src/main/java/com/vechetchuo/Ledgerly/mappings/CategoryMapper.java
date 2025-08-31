package com.vechetchuo.Ledgerly.mappings;

import com.vechetchuo.Ledgerly.models.domains.Category;
import com.vechetchuo.Ledgerly.models.dtos.account.*;
import com.vechetchuo.Ledgerly.models.dtos.category.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    GetCategoryResponse toGetDto(Category entity);

    CategoriesResponse toGetsDto(Category entity);

    Category toCreateEntity(CreateCategoryRequest dto);
    CreateCategoryResponse toCreateDto(Category entity);

    Category toUpdateEntity(UpdateCategoryRequest dto);
    UpdateCategoryResponse toUpdateDto(Category entity);

    Category toDeleteEntity(DeleteCategoryRequest dto);
    DeleteCategoryResponse toDeleteDto(Category entity);

    RecordAuditLogCategory toAuditLogDto(Category entity);
}
