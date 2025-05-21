package com.livanov.playground.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface SubjectsRepository extends ListCrudRepository<Subject, String> {
    @Query("""
                SELECT s FROM Subject s
                 WHERE EXISTS (SELECT 1 FROM s.names n WHERE n.value ILIKE concat('%', :name, '%'))
            """)
    List<Subject> findByName(String name);
}
