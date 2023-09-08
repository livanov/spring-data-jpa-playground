package com.livanov.playground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    private final String id;

    private final String name;

    public Subject(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
