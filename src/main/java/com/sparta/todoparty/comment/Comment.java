package com.sparta.todoparty.comment;


import com.sparta.todoparty.todo.Todo;
import com.sparta.todoparty.todo.TodoRequestDto;
import com.sparta.todoparty.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public Comment(CommentRequestDto commentRequestDto) {
        this.text = commentRequestDto.getText();
        this.createDate = LocalDateTime.now();

    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }
    public void setTodo(Todo todo) {
        this.todo = todo;
        todo.getComments().add(this);
    }

    // 서비스 메서드
    public void setText(String text){
        this.text = text;
    }


}
