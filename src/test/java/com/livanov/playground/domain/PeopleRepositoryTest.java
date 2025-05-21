package com.livanov.playground.domain;

import com.livanov.playground.BaseDatabaseIntegrationTest;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PeopleRepositoryTest extends BaseDatabaseIntegrationTest {

    @Autowired
    private PeopleRepository repository;

    @Test
    void name1() {

        // GIVEN
        val persistedPerson = repository.save(new Person("Lyubo"));

        // WHEN
        val fetchedPerson = repository.findByNameContainingIgnoreCase("UB");

        // THEN
        assertThat(fetchedPerson).satisfiesExactlyInAnyOrder(p -> assertThat(p.getId()).isEqualTo(persistedPerson.getId()));
    }
}
