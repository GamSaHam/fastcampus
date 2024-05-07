package com.fastcampus.boardserver.dto.request;

import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {

    private String oldPassword;
    private String newPassword;

}
