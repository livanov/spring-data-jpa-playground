package com.livanov.playground.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "subjects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class Subject {

    @Id
    @ToString.Include
    private String id;

    @Setter
    @Column(name = "code")
    @ToString.Include
    private String code;

    @ElementCollection
    @CollectionTable(
            name = "subject_names",
            joinColumns = @JoinColumn(name = "subject_id")
    )
    @ToString.Include
    private Set<Name> names;

    public Subject(String code, Name... names) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.names = Arrays.stream(names).collect(Collectors.toSet());
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @ToString
    public static class Name {

        @Column(name = "language_iso_alpha2")
        private String language;

        private String value;

        public Name(String language, String value) {
            this.language = language;
            this.value = value;
        }
    }
}
