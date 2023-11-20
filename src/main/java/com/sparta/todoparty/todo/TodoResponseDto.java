package com.sparta.todoparty.todo;


import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoResponseDto extends CommonResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;
    private Boolean isCompleted;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isCompleted = todo.getIsCompleted();
        this.userDto = new UserDto(todo.getUser());
        this.createDate = todo.getCreateDate();
    }
}
