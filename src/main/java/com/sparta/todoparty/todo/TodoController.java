package com.sparta.todoparty.todo;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.user.User;
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
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        TodoResponseDto responseDto = todoService.createTodo(todoRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId){
    try {
        TodoResponseDto responseDto = todoService.getTodoDto(todoId);
        return ResponseEntity.ok().body(responseDto);

    } catch (IllegalArgumentException e){
        return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getTodoList(){
        List<TodoListResponseDto> response = new ArrayList<>();

        Map<UserDto, List<TodoResponseDto>> responseDtoMap = todoService.getUserTodoMap();

        responseDtoMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }
    @Transactional
    @PutMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> putTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            TodoResponseDto responseDto = todoService.updateTodo(todoId, todoRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);

        }catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }
    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<CommonResponseDto> patchTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            TodoResponseDto responseDto = todoService.completeTodo(todoId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);

        }catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
