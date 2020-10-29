package com.oocl.todolistapi.controller;

import com.oocl.todolistapi.model.Todo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoListController {
    private List<Todo> todos;

    public TodoListController(List<Todo> todos) {
        this.todos = todos;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todos;
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        todos.add(todo);

        return todo;
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
