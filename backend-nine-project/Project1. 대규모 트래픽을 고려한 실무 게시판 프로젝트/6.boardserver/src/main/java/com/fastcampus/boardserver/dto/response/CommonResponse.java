package com.fastcampus.boardserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private HttpStatus status;
    private String code;
    private String message;
    private T requestBody;


}
