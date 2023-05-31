package ru.vorobyov.socialmediaapi.integration;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class IntegrationTest extends IntegrationEnvironment{
    @Test
    public void testContainer() {
        assertNotNull(getInstance());
    }
}
