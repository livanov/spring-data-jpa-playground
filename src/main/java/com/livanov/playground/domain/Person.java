package com.livanov.playground.domain;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    private String id;

    @Setter
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "people_subjects",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")}
    )
    private Set<Subject> subjects = new HashSet<>();

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Set<String> tags;

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public void addTags(String... tags) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.addAll(List.of(tags));
    }

    public void addSubjects(Subject... subjects) {
        if (this.subjects == null) {
            this.subjects = new HashSet<>();
        }
        this.subjects.addAll(List.of(subjects));
    }
}
