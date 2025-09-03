package com.vechetchuo.Ledgerly.seeds;

import com.vechetchuo.Ledgerly.models.domains.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired private RoleSeeder roleSeeder;
    @Autowired private UserSeeder userSeeder;
    @Autowired private GlobalParamSeeder globalParamSeeder;
    @Autowired private CategorySeeder categorySeeder;

    @Transactional
    @Override
    public void run(String... args) {
        roleSeeder.seed();
        User systemAdmin = userSeeder.seedSystemAdmin();
        globalParamSeeder.seed();
        categorySeeder.seed(systemAdmin);
    }
}

