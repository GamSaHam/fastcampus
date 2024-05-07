package com.fastcampus.boardserver.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class UserDeleteIdRequest {
    @NonNull
    private String userId;
    @NonNull
    private String password;
}
