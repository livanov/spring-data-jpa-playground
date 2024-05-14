package com.livanov.playground.domain;

import com.livanov.playground.BaseDatabaseIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeopleRepositoryTest extends BaseDatabaseIntegrationTest {

    @Autowired
    private PeopleRepository repository;

    @Test
    @Order(1)
    void name1() throws ExecutionException, InterruptedException {

        // GIVEN
        repository.save(new Person("Aaa"));

        // WHEN
        final Future<List<Person>> future = Executors.newCachedThreadPool().submit(() -> repository.findAll());

        // THEN
        assertThat(future.get()).satisfiesExactly(x ->
                assertThat(x.getName()).isEqualTo("Aaa")
        );
    }

    @Test
    @Order(2)
    void name2() {
        assertThat(repository.findAll()).isEmpty();
    }
}
