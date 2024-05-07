package com.fastcampus.boardserver.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class BoardServerException extends RuntimeException {

    private HttpStatus status;
    private String msg;

    public BoardServerException(HttpStatus status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }




}
