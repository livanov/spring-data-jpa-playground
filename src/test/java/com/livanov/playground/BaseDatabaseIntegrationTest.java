package com.livanov.playground;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.extension.*;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

//@AutoConfigureCache
//@AutoConfigureDataJpa
//@AutoConfigureTestEntityManager
//@ImportAutoConfiguration
@Transactional
@SpringBootTest(properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",
})
//@OverrideAutoConfiguration(enabled = false)
//@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        PlaygroundApplicationLocal.DatabaseConfiguration.class,
        BaseDatabaseIntegrationTest.TestConfiguration.class,
//        MyDataSource.class
})
@AutoConfigureTestEntityManager
@ExtendWith(BaseDatabaseIntegrationTest.MyExtension.class)
public abstract class BaseDatabaseIntegrationTest {

    static class TestConfiguration {

        @Bean
        @LiquibaseDataSource
        DataSource liquibaseDataSource(PostgreSQLContainer<?> container) throws SQLException {

            val x = new PGSimpleDataSource();

            x.setUrl(container.getJdbcUrl());
            x.setUser(container.getUsername());
            x.setPassword(container.getPassword());
            x.setDatabaseName(container.getDatabaseName());

            x.getConnection().createStatement()
                    .execute("CREATE DATABASE customtemplate OWNER %s".formatted(container.getUsername()));

            val pgSimpleDataSource = new PGSimpleDataSource();

            pgSimpleDataSource.setUrl(container.getJdbcUrl());
            pgSimpleDataSource.setUser(container.getUsername());
            pgSimpleDataSource.setPassword(container.getPassword());
            pgSimpleDataSource.setDatabaseName("customtemplate");

            return pgSimpleDataSource;
        }

        @Bean
        @Primary
        DataSource myDataSource(PostgreSQLContainer<?> container) {
            return new DataSourcePerTestMethod(container, "customtemplate");
        }
    }

    public static class MyExtension implements InvocationInterceptor {

        @Override
        public void interceptTestMethod(
                Invocation<Void> invocation,
                ReflectiveInvocationContext<Method> invocationContext,
                ExtensionContext extensionContext
        ) throws Throwable {

            val myDataSource = SpringExtension.getApplicationContext(extensionContext).getBean(DataSourcePerTestMethod.class);

            myDataSource.prepareFor(invocationContext.getExecutable());

            invocation.proceed();
        }
    }

    static class DataSourcePerTestMethod implements DataSource {

        //    private final ThreadLocal<DataSource> dataSources;
        private final Map<String, DataSource> dataSources;
        private final PostgreSQLContainer<?> container;
        private final String templateName;

        public DataSourcePerTestMethod(PostgreSQLContainer<?> container, String templateName) {
            this.container = container;
            this.templateName = templateName;
    //        this.dataSources = ThreadLocal.withInitial(() -> cloneDataSource(container, templateName));
            this.dataSources = new HashMap<>();
        }

        @SneakyThrows
        private static PGSimpleDataSource cloneDataSource(PostgreSQLContainer<?> container, String templateName) {

            val dbName = RandomStringUtils.randomAlphabetic(10).toLowerCase();

            container.createConnection("").createStatement()
                    .execute("CREATE DATABASE %s WITH TEMPLATE %s OWNER %s;".formatted(
                            dbName, templateName, container.getUsername()
                    ));

            val pgSimpleDataSource = new PGSimpleDataSource();

            pgSimpleDataSource.setUrl(container.getJdbcUrl());
            pgSimpleDataSource.setUser(container.getUsername());
            pgSimpleDataSource.setPassword(container.getPassword());
            pgSimpleDataSource.setDatabaseName(dbName);

            return pgSimpleDataSource;
        }

        @Override
        public Connection getConnection() throws SQLException {
            return get().getConnection();
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return get().getConnection(username, password);
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return get().getLogWriter();
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
            get().setLogWriter(out);
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            get().setLoginTimeout(seconds);
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return get().getLoginTimeout();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return get().getParentLogger();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return get().unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return get().isWrapperFor(iface);
        }

        public void prepareFor(Method method) {
            this.dataSources.put(
                    "%s.%s".formatted(method.getDeclaringClass().getCanonicalName(), method.getName()),
                    cloneDataSource(container, templateName)
            );
        }

        private DataSource get() {
            return Arrays.stream(Thread.currentThread().getStackTrace())
                    .map(frame -> "%s.%s".formatted(frame.getClassName(), frame.getMethodName()))
                    .map(dataSources::get) // we could get the method annotated with `@Test` instead
                    .filter(Objects::nonNull)
                    .findAny()
                    .orElseGet(() -> cloneDataSource(container, templateName));
        }
    }
}