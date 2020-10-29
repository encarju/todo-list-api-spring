package com.oocl.todolistapi.exception;

import static java.lang.String.format;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Integer id) {
        super(format("Todo with ID %d not found", id));
    }
}
