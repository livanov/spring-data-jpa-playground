package com.livanov.playground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    private String id;
    private String name;
    @ManyToMany
    private List<Subject> subjects = new ArrayList<>();

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
