package com.livanov.playground.web;

import com.livanov.playground.domain.Subject;
import com.livanov.playground.domain.SubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/people")
@RequiredArgsConstructor
public class SubjectsController {

    private final SubjectsRepository repository;

    @GetMapping
    public List<Subject> all(@RequestParam(required = false) String name) {
        return Optional.ofNullable(name)
                .map(repository::findByName)
                .orElseGet(repository::findAll);
    }

    @GetMapping("{id}")
    public Subject one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow();
    }
}
