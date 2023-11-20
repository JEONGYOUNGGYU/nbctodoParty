package com.sparta.todoparty.todo;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDetailsImpl;
import com.sparta.todoparty.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        TodoResponseDto responseDto = todoService.createPost(todoRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId){
    try {
        TodoResponseDto responseDto = todoService.getTodo(todoId);
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
}
