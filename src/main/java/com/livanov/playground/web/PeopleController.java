package com.livanov.playground.web;

import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import com.livanov.playground.domain.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/people")
@RequiredArgsConstructor
class PeopleController {

    private final PeopleRepository repository;

    @GetMapping
    public List<Person> all(@RequestParam(required = false) String name) {
        return Optional.ofNullable(name)
                .map(repository::findByName)
                .orElseGet(repository::findAll);
    }

    @GetMapping("{id}")
    public Person one(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Transactional
    @GetMapping("test")
    public void asd() {
        Person person = repository.findAll().stream()
                .findAny().get();

        person.setName("aaaaaaaaaaaaaaa");

        Subject subject = person.getSubjects().stream().findFirst().get();

        subject.setCode("zzzzzzzzzzzzzzzz");

        repository.save(person);
    }
}
