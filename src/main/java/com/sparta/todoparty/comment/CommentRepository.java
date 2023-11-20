package com.sparta.todoparty.comment;

import com.sparta.todoparty.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
