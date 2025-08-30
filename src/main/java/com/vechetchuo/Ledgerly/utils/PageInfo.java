package com.vechetchuo.Ledgerly.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PageInfo {
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalRecords;
}
