package com.vechetchuo.Ledgerly.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;

public class PaginationUtil {

    public static PageRequest toPageRequest(PaginationRequest request) {
        List<Sort.Order> orders = request.getFilter().getSort().stream()
                .map(opt ->{
                    if(opt.getDirection().isEmpty() || opt.getProperty().isEmpty()){
                        return new Sort.Order(Sort.Direction.DESC, "id");
                    }
                    return  new Sort.Order(
                            opt.getDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                            opt.getProperty());
                })
                .toList();

        Sort sort = orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
        return PageRequest.of(request.getPage() - 1, request.getPageSize(), sort);
    }
}

