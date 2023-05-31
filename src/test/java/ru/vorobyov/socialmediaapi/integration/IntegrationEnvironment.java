package ru.vorobyov.socialmediaapi.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

public class IntegrationEnvironment {
    private static final PostgreSQLContainer<?> container;

    static {
        container = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("scrapper")
                .withUsername("postgres")
                .withPassword("postgres");
        container.start();
        Startables.deepStart(container);
    }

    public PostgreSQLContainer<?> getInstance() {
        return container;
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
