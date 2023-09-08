package com.livanov.playground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {

    @Id
    private String id;

    private String name;

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
