package com.vechetchuo.Ledgerly.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PaginationRequest {
    private int page;
    private int pageSize;
    private PropertyFilter filter = new PropertyFilter();

    @Data
    @NoArgsConstructor
    public static class PropertyFilter {
        private String search;
        private List<SortOption> sort = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    public static class SortOption {
        private String property = "id";
        private String direction = "desc";
    }
}

