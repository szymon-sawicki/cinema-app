package com.cinema.app.infrastructure.configs;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan("com.cinema.app")
@PropertySource("classpath:application.properties")

public class AppSpringConfig {

    @Value("${jdbi.url}")
    private String jdbiUrl;

    @Value("${jdbi.user")
    private String jdbiUsername;

    @Value("${jdbi.password")
    private String jdbiPassword;

    @Bean
    public Jdbi jdbi() {
        return Jdbi.create(jdbiUrl, jdbiUsername, jdbiPassword);
    }
}
