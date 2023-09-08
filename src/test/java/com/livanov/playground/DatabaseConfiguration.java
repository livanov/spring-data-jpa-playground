package com.livanov.playground;

import lombok.val;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class DatabaseConfiguration {

    @Bean
    @RestartScope
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSqlContainer() {
        val imageName = DockerImageName.parse("postgres").withTag("14.6-alpine");

        return new PostgreSQLContainer<>(imageName)
                .withUsername("postgres")
                .withPassword("postgres");
    }
}
