package com.vechetchuo.Ledgerly.models.dtos.category;

import com.vechetchuo.Ledgerly.utils.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoriesResponse {
    private List<CategoriesResponse> categories;
    private PageInfo pageInfo;
}
