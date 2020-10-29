package com.oocl.todolistapi.service;

import com.oocl.todolistapi.exception.TodoNotFoundException;
import com.oocl.todolistapi.model.Todo;
import com.oocl.todolistapi.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static com.oocl.todolistapi.TestConstants.NONE;
import static com.oocl.todolistapi.TestConstants.ONCE;
import static com.oocl.todolistapi.TestConstants.TODO_1;
import static com.oocl.todolistapi.TestConstants.WRONG_ID;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoServiceTest {

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
        todo.setText(TODO_1);
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
        todo.setText(TODO_1);
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

    @Test
    void should_return_a_todo_list_not_found_exception_when_update_todo_given_todo_and_wrong_todo_id() {
        //Given
        when(todoRepository.findById(WRONG_ID)).thenReturn(empty());

        Todo updatedTodo = new Todo();
        updatedTodo.setDone(true);

        //When
        Executable executable = () -> todoService.update(WRONG_ID, updatedTodo);

        //Then
        Exception exception = assertThrows(TodoNotFoundException.class, executable);
        assertEquals(format("Todo with ID %d not found", WRONG_ID), exception.getMessage());
        verify(todoRepository, times(ONCE)).findById(WRONG_ID);
        verify(todoRepository, times(NONE)).save(any(Todo.class));
    }

    @Test
    void should_delete_todo_when_delete_todo_given_todo_id() {
        //Given
        Integer id = 1;
        Todo todo = new Todo();
        todo.setId(id);
        todo.setText(TODO_1);
        todo.setDone(false);
        when(todoRepository.findById(id)).thenReturn(of(todo));

        //When
        todoService.delete(id);

        //Then
        verify(todoRepository, times(ONCE)).findById(id);
        verify(todoRepository, times(ONCE)).delete(todo);
    }

    @Test
    void should_return_a_todo_list_not_found_exception_when_delete_todo_given_wrong_todo_id() {
        //Given
        when(todoRepository.findById(WRONG_ID)).thenReturn(empty());

        //When
        Executable executable = () -> todoService.delete(WRONG_ID);

        //Then
        Exception exception = assertThrows(TodoNotFoundException.class, executable);
        assertEquals(format("Todo with ID %d not found", WRONG_ID), exception.getMessage());
        verify(todoRepository, times(ONCE)).findById(WRONG_ID);
        verify(todoRepository, times(NONE)).save(any(Todo.class));
    }
}