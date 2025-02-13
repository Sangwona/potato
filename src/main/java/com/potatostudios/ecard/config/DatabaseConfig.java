package com.potatostudios.ecard.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public CommandLineRunner printDatabaseUrl() {
        return args -> System.out.println("🔍 Database URL: " + dbUrl);
    }
}