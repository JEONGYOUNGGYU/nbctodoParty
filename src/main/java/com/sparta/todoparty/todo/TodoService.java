package com.sparta.todoparty.todo;

import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createPost(TodoRequestDto todoRequestDto, User user){
        Todo todo = new Todo(todoRequestDto);
        todo.setUser(user);

        todoRepository.save(todo);

        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));
        return new TodoResponseDto(todo);

    }

    public Map<UserDto, List<TodoResponseDto>> getUserTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();

        // 작성일 기준 내림차순
        List<Todo> todoList = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        todoList.stream().forEach(todo -> {
            var userDto = new UserDto(todo.getUser());
            var todoDto = new TodoResponseDto(todo);
            if(userTodoMap.containsKey(userDto)) {
                // 유저 할일목록에 항목을 추가
                userTodoMap.get(userDto).add(todoDto);

            } else{
                // 유저 할일목록을 새로 추가
                userTodoMap.put(userDto, new ArrayList<>(List.of(todoDto)));

            }
        });
        return userTodoMap;
    }
}
