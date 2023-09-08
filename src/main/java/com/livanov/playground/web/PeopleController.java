package com.livanov.playground.web;

import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/people")
@RequiredArgsConstructor
public class PeopleController {

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
}
