package com.oocl.todolistapi.service;

import com.oocl.todolistapi.model.Todo;
import com.oocl.todolistapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
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

    @Test
    void should_return_the_created_todo_when_create_todo_given_todo() {
        //Given
        Todo todo = new Todo();
        todo.setId(1);
        todo.setText("Todo 1");
        todo.setDone(false);

        when(todoRepository.save(todo)).thenReturn(todo);

        //When
        Todo returnedTodo = todoService.create(todo);

        //Then
        assertEquals(todo.getId(), returnedTodo.getId());
        assertEquals(todo.getText(), returnedTodo.getText());
        assertEquals(todo.isDone(), returnedTodo.isDone());
        verify(todoRepository, times(ONCE)).save(todo);
    }

    @Test
    void should_return_the_updated_todo_when_update_todo_given_todo_and_todo_id() {
        //Given
        Integer id = 1;
        Todo todo = new Todo();
        todo.setId(id);
        todo.setText("Todo 1");
        todo.setDone(false);
        when(todoRepository.findById(id)).thenReturn(of(todo));
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo updatedTodo = new Todo();
        updatedTodo.setDone(true);

        //When
        Todo returnedTodo = todoService.update(id, updatedTodo);

        //Then
        assertEquals(todo.getId(), returnedTodo.getId());
        assertEquals(todo.getText(), returnedTodo.getText());
        assertEquals(updatedTodo.isDone(), returnedTodo.isDone());
        verify(todoRepository, times(ONCE)).findById(id);
        verify(todoRepository, times(ONCE)).save(todo);
    }
}