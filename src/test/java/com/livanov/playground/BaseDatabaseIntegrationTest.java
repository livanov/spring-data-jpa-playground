package com.livanov.playground;

import lombok.val;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

//@AutoConfigureCache
@AutoConfigureDataJpa
//@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@SpringBootTest
@OverrideAutoConfiguration(enabled = false)
//@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        PlaygroundApplicationLocal.DatabaseConfiguration.class,
        BaseDatabaseIntegrationTest.TestConfiguration.class
})
public abstract class BaseDatabaseIntegrationTest {

    static class TestConfiguration {

        @Bean
        DataSource dataSource(PostgreSQLContainer<?> container) throws SQLException {
            container.createConnection("").createStatement()
                    .execute("CREATE DATABASE lyubo TEMPLATE test");
            container.createConnection("").createStatement()
                    .execute("DROP DATABASE lyubo TEMPLATE test");

            PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
            pgSimpleDataSource.setUrl(container.getJdbcUrl());
            pgSimpleDataSource.setUser(container.getUsername());
            pgSimpleDataSource.setPassword(container.getPassword());
            pgSimpleDataSource.setDatabaseName("lyubo");
//            pgSimpleDataSource.setCurrentSchema(UUID.randomUUID().toString());
//            try (val statement = pgSimpleDataSource.getConnection().createStatement()) {
//                statement.executeQuery()
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
            return pgSimpleDataSource;
        }
    }
}