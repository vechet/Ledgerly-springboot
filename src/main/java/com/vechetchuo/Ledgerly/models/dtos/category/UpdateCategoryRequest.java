package com.vechetchuo.Ledgerly.models.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
    @NotNull(message = "This field Id is required!")
    private Integer id;
    @NotBlank(message = "This field Name is required!")
    private String name;
    private int parentId;
    private String memo;
}
