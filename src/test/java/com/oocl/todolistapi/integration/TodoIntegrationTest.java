package com.oocl.todolistapi.integration;

import com.oocl.todolistapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    void should_return_all_todos_when_get_all() throws Exception {
        //Given
        //When
        //Then
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void should_return_the_created_todo_response_when_create_given_todo_request() throws Exception {
        //Given
        String todoRequest = "{\n" +
                "   \"text\": \"Todo 1\"\n" +
                "}";

        //When
        //Then
        mockMvc.perform(post("/todos")
                .contentType(APPLICATION_JSON)
                .content(todoRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Todo 1"))
                .andExpect(jsonPath("$.done").value(false));
    }
}