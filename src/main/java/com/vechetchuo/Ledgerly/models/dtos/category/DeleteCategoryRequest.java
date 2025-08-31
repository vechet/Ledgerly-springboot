package com.vechetchuo.Ledgerly.models.dtos.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCategoryRequest {
    @NotNull(message = "This field Name is required!")
    private int id;
}
