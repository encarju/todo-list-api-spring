package com.oocl.todolistapi.controller;

import com.oocl.todolistapi.dto.TodoRequest;
import com.oocl.todolistapi.dto.TodoResponse;
import com.oocl.todolistapi.model.Todo;
import com.oocl.todolistapi.service.TodoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.oocl.todolistapi.mapper.TodoMapper.TODO_MAPPER;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/todos")
public class TodoListController {
    private List<Todo> todos = new ArrayList<>();
    private TodoService todoService;

    public TodoListController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoResponse> getTodos() {
        return TODO_MAPPER.toResponse(todoService.getAll());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TodoResponse addTodo(@RequestBody TodoRequest todoRequest) {
        Todo todo = TODO_MAPPER.toEntity(todoRequest);

        return TODO_MAPPER.toResponse(todoService.create(todo));
    }

    @PutMapping("/{id}")
    public Todo updateTodoStatus(@PathVariable Integer id, @RequestBody Todo updatedTodo) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .map(todo -> {
                    todo.setDone(updatedTodo.isDone());

                    return todo;
                }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Integer id) {
        todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .map(todo -> todos.remove(todo))
                .orElse(null);
    }
}
