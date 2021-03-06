package com.oocl.todolistapi.integration;

import com.oocl.todolistapi.model.Todo;
import com.oocl.todolistapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.oocl.todolistapi.TestConstants.FORMATTED_TODO_EXCEPTION_MESSAGE;
import static com.oocl.todolistapi.TestConstants.NOT_FOUND;
import static com.oocl.todolistapi.TestConstants.STATUS_404;
import static com.oocl.todolistapi.TestConstants.TODO_1;
import static com.oocl.todolistapi.TestConstants.WRONG_ID;
import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    }

    @Test
    void should_return_the_created_todo_response_when_create_given_todo_request() throws Exception {
        //Given
        String todoRequest = "{\n" +
                "   \"text\": \"" + TODO_1 + "\"\n" +
                "}";

        //When
        //Then
        mockMvc.perform(post("/todos")
                .contentType(APPLICATION_JSON)
                .content(todoRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value(TODO_1))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_return_updated_todo_when_update_given_id_and_updated_to_do_request() throws Exception {
        //Given
        Todo todo = new Todo();
        todo.setText(TODO_1);
        todo.setDone(false);
        Integer returnedTodoId = todoRepository.save(todo).getId();

        String newTodoText = "Todo 2";
        String todoRequest = "{\n" +
                "    \"text\" : \"" + newTodoText + "\",\n" +
                "    \"done\" : \"true\"\n" +
                "}";

        //When
        //Then
        mockMvc.perform(put(format("/todos/%d", returnedTodoId))
                .contentType(APPLICATION_JSON)
                .content(todoRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(returnedTodoId))
                .andExpect(jsonPath("$.text").value(newTodoText))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_return_not_found_status_when_update_given_wrong_id_and_updated_to_do_request() throws Exception {
        //Given
        String todoRequest = "{\n" +
                "    \"done\" : \"true\"\n" +
                "}";
        //When
        //Then
        mockMvc.perform(put(format("/todos/%d", WRONG_ID))
                .contentType(APPLICATION_JSON)
                .content(todoRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(NOT_FOUND))
                .andExpect(jsonPath("$.errorMessage").value(format(FORMATTED_TODO_EXCEPTION_MESSAGE, WRONG_ID)))
                .andExpect(jsonPath("$.status").value(STATUS_404));
    }

    @Test
    void should_delete_todo_when_delete_given_id_() throws Exception {
        //Given
        Todo todo = new Todo();
        todo.setText(TODO_1);
        todo.setDone(false);
        Integer returnedTodoId = todoRepository.save(todo).getId();

        //When
        //Then
        mockMvc.perform(delete(format("/todos/%d", returnedTodoId)))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_not_found_status_when_delete_given_wrong_id() throws Exception {
        //Given
        //When
        //Then
        mockMvc.perform(delete(format("/todos/%d", WRONG_ID)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(NOT_FOUND))
                .andExpect(jsonPath("$.errorMessage").value(format(FORMATTED_TODO_EXCEPTION_MESSAGE, WRONG_ID)))
                .andExpect(jsonPath("$.status").value(STATUS_404));
    }
}
