package com.oocl.todolistapi.service;

import com.oocl.todolistapi.model.Todo;
import com.oocl.todolistapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoServiceTest {
    private static final int ONCE = 1;
    private TodoService todoService;
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = mock(TodoRepository.class);
        todoService = new TodoService(todoRepository);
    }

    @Test
    void should_return_all_todo_when_get_todos_() {
        //Given
        List<Todo> todos = asList(new Todo());
        when(todoRepository.findAll()).thenReturn(todos);
        //When
        List<Todo> returnedTodos = todoService.getAll();
        //Then
        assertNotNull(returnedTodos);
        assertEquals(todos.size(), returnedTodos.size());
        verify(todoRepository, times(ONCE)).findAll();
    }
}