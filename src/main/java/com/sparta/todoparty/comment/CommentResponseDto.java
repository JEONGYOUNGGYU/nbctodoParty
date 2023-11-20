package com.sparta.todoparty.comment;


import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.todo.Todo;
import com.sparta.todoparty.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends CommonResponseDto {
    private Long id;
    private String text;
    private UserDto user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDto(comment.getUser());
        this.createDate = comment.getCreateDate();
    }
}
