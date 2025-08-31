package com.vechetchuo.Ledgerly.controllers;

import com.vechetchuo.Ledgerly.models.dtos.category.*;
import com.vechetchuo.Ledgerly.services.CategoryService;
import com.vechetchuo.Ledgerly.utils.ApiResponse;
import com.vechetchuo.Ledgerly.utils.PaginationRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category", description = "the Category Api")
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/v1/category/get-category")
    public ApiResponse<GetCategoryResponse> getCategory(@Valid @RequestBody GetCategoryRequest req){
        return categoryService.getCategory(req);
    }

    @PostMapping("/v1/category/get-categories")
    public ApiResponse<GetCategoriesResponse> getCategories(@Valid @RequestBody PaginationRequest req){
        return categoryService.getCategories(req);
    }

    @PostMapping("/v1/category/create-category")
    public ApiResponse<CreateCategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest req){
        return categoryService.createCategory(req);
    }

    @PostMapping("/v1/category/update-category")
    public ApiResponse<UpdateCategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest req){
        return categoryService.updateCategory(req);
    }

    @PostMapping("/v1/category/delete-category")
    public ApiResponse<DeleteCategoryResponse> deleteCategory(@Valid @RequestBody DeleteCategoryRequest req){
        return categoryService.deleteCategory(req);
    }
}
