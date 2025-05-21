package com.livanov.playground;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({
        PlaygroundApplicationLocal.DatabaseConfiguration.class,
})
public abstract class BaseDatabaseIntegrationTest {
}