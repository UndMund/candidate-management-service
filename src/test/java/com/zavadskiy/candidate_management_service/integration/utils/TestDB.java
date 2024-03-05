package com.zavadskiy.candidate_management_service.integration.utils;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestDB extends PostgreSQLContainer<TestDB> {
    private static final String IMAGE_VERSION = "postgres:15";
    private static TestDB container;

    private TestDB() {
        super(IMAGE_VERSION);
    }

    public static TestDB getInstance() {
        if (container == null) {
            container = new TestDB();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
    }
}
