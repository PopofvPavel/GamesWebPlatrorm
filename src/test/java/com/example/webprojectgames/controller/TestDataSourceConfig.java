package com.example.webprojectgames.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
public class TestDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new DriverManagerDataSource(); // Создаем заглушку DataSource
    }
}
