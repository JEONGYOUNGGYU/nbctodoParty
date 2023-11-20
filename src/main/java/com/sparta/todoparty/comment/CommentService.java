package com.sparta.todoparty.comment;

import com.sparta.todoparty.todo.*;
import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final TodoService todoService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user){
        Todo todo = todoService.getTodo(commentRequestDto.getTodoId());

        Comment comment = new Comment(commentRequestDto);
        comment.setUser(user);
        comment.setTodo(todo);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = getUserComment(commentId, user);

        comment.setText(commentRequestDto.getText());

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user){
        Comment comment = getUserComment(commentId, user);

        commentRepository.delete(comment);
    }

    // 계속 사용되는 getTodo 빼주기
    public Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));

        if(user.getId().equals(comment.getUser().getId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }


}

