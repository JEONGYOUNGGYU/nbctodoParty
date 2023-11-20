package com.sparta.todoparty.comment;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.todo.*;
import com.sparta.todoparty.user.UserDetailsImpl;
import com.sparta.todoparty.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        /*Todo todo = todoService.getTodo(commentRequestDto.getTodoId());*/

        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDto);
    }

    @Transactional
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommonResponseDto commonResponseDto = commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(commonResponseDto);

        }catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));

        }catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
