package com.vechetchuo.Ledgerly.seeds;


import com.vechetchuo.Ledgerly.enums.EnumGlobalParam;
import com.vechetchuo.Ledgerly.enums.EnumGlobalParamType;
import com.vechetchuo.Ledgerly.models.domains.GlobalParam;
import com.vechetchuo.Ledgerly.repositories.GlobalParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class GlobalParamSeeder {

    @Autowired private GlobalParamRepository globalParamRepository;

    @Transactional
    public void seed() {
        if (globalParamRepository.count() > 0) return;

        List<GlobalParam> params = new ArrayList<>();
        for (EnumGlobalParamType type : EnumGlobalParamType.values()) {
            for (EnumGlobalParam status : List.of(EnumGlobalParam.Normal, EnumGlobalParam.Deleted)) {
                params.add(new GlobalParam(status.getMessage(), status.getMessage(), type.getMessage()));
            }
        }
        globalParamRepository.saveAll(params);
    }
}

