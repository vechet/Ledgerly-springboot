package com.vechetchuo.Ledgerly.models.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesResponse {
    private int id;
    private String name;
    private int parentId;
    private String parentName;
    private String memo;
    private boolean defaultCategory;
}
