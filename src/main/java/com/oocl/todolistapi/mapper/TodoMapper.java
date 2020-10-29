package com.oocl.todolistapi.mapper;

import com.oocl.todolistapi.dto.TodoResponse;
import com.oocl.todolistapi.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TodoMapper {
    TodoMapper TODO_MAPPER = Mappers.getMapper(TodoMapper.class);

    TodoResponse toResponse(Todo todo);

    List<TodoResponse> toResponse(List<Todo> todos);
}
