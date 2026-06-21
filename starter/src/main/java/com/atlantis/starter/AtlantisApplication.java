package com.atlantis.starter;

import com.atlantis.backend.config.BackendConfig;
import com.atlantis.frontend.config.FrontendConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        BackendConfig.class,
        FrontendConfig.class
})
public class AtlantisApplication {
    public static void main(String[] args) {
        SpringApplication.run(AtlantisApplication.class, args);
    }
}