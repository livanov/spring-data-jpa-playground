package com.livanov.playground.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "subjects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject {

    @Id
    private String id;

    @Column(name = "code")
    private String code;

    @ElementCollection
    @CollectionTable(
            name = "subject_names",
            joinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Name> names;

    Subject(String code, Name... names) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.names = Arrays.stream(names).toList();
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    static class Name {

        @Column(name = "language_iso_alpha2")
        String language;

        String value;
    }
}
