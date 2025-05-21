package com.livanov.playground.domain;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PeopleRepository extends ListCrudRepository<Person, Integer> {
    List<Person> findByNameContainingIgnoreCase(String name);
}
