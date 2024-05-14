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
@Table(name = "subjects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject {

    @Id
    private String id;
    private String name;

    public Subject(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
