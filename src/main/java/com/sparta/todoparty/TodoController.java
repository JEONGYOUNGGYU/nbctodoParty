package com.sparta.todoparty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @GetMapping
    public ResponseEntity<Void> getTodoList(){
        return ResponseEntity.ok().build();
    }
}
