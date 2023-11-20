package com.sparta.todoparty.todo;

import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoListResponseDto {
    private UserDto user;
    private List<TodoResponseDto> todoList;

    public TodoListResponseDto(UserDto user, List<TodoResponseDto> todoList){
        this.user = user;
        this.todoList = todoList;

    }

}
