package com.livanov.playground.web;

import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .map(repository::findByNameContainingIgnoreCase)
                .orElseGet(repository::findAll);
    }

    @GetMapping("{id}")
    public Person one(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow();
    }
}
