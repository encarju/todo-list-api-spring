package com.oocl.todolistapi.service;

import com.oocl.todolistapi.exception.TodoNotFoundException;
import com.oocl.todolistapi.model.Todo;
import com.oocl.todolistapi.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo update(Integer id, Todo updatedTodo) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setText(updatedTodo.getText());
                    todo.setDone(updatedTodo.isDone());

                    return todoRepository.save(todo);
                }).orElseThrow(() -> new TodoNotFoundException(id));
    }

    public void delete(Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (!todo.isPresent()) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.delete(todo.get());
    }
}
