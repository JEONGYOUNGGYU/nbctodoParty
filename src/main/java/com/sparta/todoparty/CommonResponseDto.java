package com.sparta.todoparty;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)    // null 값이 있으면 넣지 않게하는 애
public class CommonResponseDto {
    private String msg;
    private Integer statusCode;
}
