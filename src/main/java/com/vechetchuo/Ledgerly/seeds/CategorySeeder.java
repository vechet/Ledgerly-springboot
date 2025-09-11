package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.enums.EnumGlobalParam;
import com.vechetchuo.Ledgerly.enums.EnumGlobalParamType;
import com.vechetchuo.Ledgerly.models.domains.Category;
import com.vechetchuo.Ledgerly.models.domains.User;
import com.vechetchuo.Ledgerly.repositories.CategoryRepository;
import com.vechetchuo.Ledgerly.repositories.GlobalParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CategorySeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GlobalParamRepository globalParamRepository;

    public void seed(User user) {
        if (categoryRepository.count() > 0) return;

        var status = globalParamRepository.findStatusByKeyNameAndType(
                EnumGlobalParam.Normal.getMessage(),
                EnumGlobalParamType.CategoryxxxStatus.getMessage()
        );

        Category category = new Category();
        category.setName("Main");
        category.setUserId(String.valueOf(user.getId()));
        category.setSystemValue(true);
        category.setGlobalParam(status);
        category.setCreatedBy(String.valueOf(user.getId()));
        category.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(category);
    }
}

