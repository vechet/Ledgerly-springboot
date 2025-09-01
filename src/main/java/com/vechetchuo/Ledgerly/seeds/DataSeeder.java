package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.enums.EnumGlobalParam;
import com.vechetchuo.Ledgerly.enums.EnumGlobalParamType;
import com.vechetchuo.Ledgerly.models.domains.Category;
import com.vechetchuo.Ledgerly.models.domains.GlobalParam;
import com.vechetchuo.Ledgerly.repositories.CategoryRepository;
import com.vechetchuo.Ledgerly.repositories.GlobalParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GlobalParamRepository globalParamRepository;

    @Override
    public void run(String... args) {
        if (globalParamRepository.count() == 0) {
            var globalParams = new ArrayList<GlobalParam>();

            for (var type : EnumGlobalParamType.values()) {
                var globalParamCategoryNormal = new GlobalParam();
                globalParamCategoryNormal.setName(EnumGlobalParam.Normal.getMessage());
                globalParamCategoryNormal.setKeyName(EnumGlobalParam.Normal.getMessage());
                globalParamCategoryNormal.setType(type.getMessage());
                globalParams.add(globalParamCategoryNormal);

                var globalParamCategoryDeleted = new GlobalParam();
                globalParamCategoryDeleted.setName(EnumGlobalParam.Deleted.getMessage());
                globalParamCategoryDeleted.setKeyName(EnumGlobalParam.Deleted.getMessage());
                globalParamCategoryDeleted.setType(type.getMessage());
                globalParams.add(globalParamCategoryDeleted);
            }

            globalParamRepository.saveAll(globalParams);
        }

        if (categoryRepository.count() == 0) {
            var status = globalParamRepository.findStatusByKeyNameAndType(EnumGlobalParam.Normal.getMessage(), EnumGlobalParamType.CategoryxxxStatus.getMessage());
            var category = new Category();
            category.setName("Main");
            category.setUserId("1");
            category.setSystemValue(true);
            category.setGlobalParam(status);
            category.setCreatedBy("1");
            category.setCreatedDate(LocalDateTime.now());
            categoryRepository.save(category);
        }

    }
}

