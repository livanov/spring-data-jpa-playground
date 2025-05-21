package com.livanov.playground.domain;

import com.livanov.playground.BaseDatabaseIntegrationTest;
import jakarta.persistence.EntityManagerFactory;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeopleRepositoryTest extends BaseDatabaseIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PeopleRepository people;

    @Autowired
    private SubjectsRepository subjects;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    @Order(1)
    void name1() throws ExecutionException, InterruptedException {

        // GIVEN
        testEntityManager.persistAndFlush(new Person("Aaa"));

        // WHEN
//        final Future<List<Person>> people = Executors.newCachedThreadPool().submit(() -> repository.findAll()).get();
        val people = this.people.findAll();

        // THEN
        assertThat(people).satisfiesExactly(x -> {
            assertThat(x.getName()).isEqualTo("Aaa");
            assertThat(x.getSubjects()).isEmpty();
        });
    }

    @Test
    @Order(2)
    void name2() {
        assertThat(people.findAll()).isEmpty();
    }

}
