package com.livanov.playground.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PeopleController.class)
class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void name() throws Exception {

        // GIVEN

        // WHEN
        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk());
    }
}
