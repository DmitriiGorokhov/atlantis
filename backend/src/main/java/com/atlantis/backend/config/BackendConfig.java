package com.atlantis.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.atlantis.backend")
@EnableJpaRepositories(basePackages = "com.atlantis.backend.database.repository")
@EntityScan(basePackages = "com.atlantis.backend.database.entity")
public class BackendConfig {
}