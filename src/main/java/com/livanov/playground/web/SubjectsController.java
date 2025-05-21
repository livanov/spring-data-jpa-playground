package com.livanov.playground.web;

import com.livanov.playground.domain.Subject;
import com.livanov.playground.domain.SubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("api/subjects")
@RequiredArgsConstructor
public class SubjectsController {

    private final SubjectsRepository repository;

    @GetMapping
    public List<Subject> all(@RequestParam(required = false) String name) {
        return repository.findAll().stream()
                .filter(s -> s.getNames().stream().anyMatch(n -> n.getValue().toLowerCase().contains(name.toLowerCase())))
                .toList();
    }

    @GetMapping("{id}")
    public Subject one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow();
    }
}
