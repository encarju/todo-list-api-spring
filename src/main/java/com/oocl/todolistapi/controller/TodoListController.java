package com.oocl.todolistapi.controller;

import com.oocl.todolistapi.model.Todo;
import org.springframework.web.bind.annotation.GetMapping;
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
}
