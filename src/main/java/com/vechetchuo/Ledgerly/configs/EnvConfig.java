package com.vechetchuo.Ledgerly.configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Value("${env.path}")
    private String envPath;

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
                .directory(envPath) // absolute or relative
                .filename(".env")
                .load();
    }
    //    @Autowired private Dotenv dotenv;

    //    String mailUser = dotenv.get("MAIL_USERNAME");
    //    String resetUrl = dotenv.get("RESET_BASE_URL");
}
