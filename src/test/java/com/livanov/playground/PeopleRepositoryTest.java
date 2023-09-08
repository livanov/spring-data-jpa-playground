package com.livanov.playground;

import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseConfiguration.class)
class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository repository;

    @Test
    void name() {
        repository.save(new Person("Az"));
    }
}
