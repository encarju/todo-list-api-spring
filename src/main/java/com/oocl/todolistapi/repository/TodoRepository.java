package com.oocl.todolistapi.repository;

import com.oocl.todolistapi.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
