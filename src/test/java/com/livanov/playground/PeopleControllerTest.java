package com.livanov.playground;

import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import com.livanov.playground.web.PeopleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PeopleController.class)
class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleRepository repository;

    @Test
    void name() throws Exception {

        // GIVEN
        when(repository.findAll()).thenReturn(List.of(
                new Person("Lyubo"),
                new Person("Ivan")
        ));

        // WHEN
        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk());
    }
}
