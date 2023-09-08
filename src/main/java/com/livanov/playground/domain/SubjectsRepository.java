package com.livanov.playground.domain;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface SubjectsRepository extends ListCrudRepository<Subject, String> {
    List<Subject> findByName(String name);
}
