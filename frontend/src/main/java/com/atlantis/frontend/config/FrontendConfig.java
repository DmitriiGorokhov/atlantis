package com.atlantis.frontend.config;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableVaadin("com.atlantis.frontend")
@ComponentScan(basePackages = "com.atlantis.frontend")
public class FrontendConfig {
}