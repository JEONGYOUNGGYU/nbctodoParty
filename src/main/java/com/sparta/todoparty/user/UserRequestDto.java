package com.sparta.todoparty.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")        // {4자이상 10자미만} $ = 끝이라는 표시
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;
}
