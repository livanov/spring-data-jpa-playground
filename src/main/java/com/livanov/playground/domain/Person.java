package com.livanov.playground.domain;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Person {

    @Id
    private String id;

    @Setter
    private String name;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Set<String> tags;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "people_subjects",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")}
    )
    private Set<Subject> subjects;

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
