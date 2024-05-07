package com.fastcampus.boardserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class UserDTO {
    public boolean hasNullBeforeRegister(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickname() == null;
    }
    public enum Status {
        DEFAULT, ADMIN, DELETE
    }
    private Long id;
    private String userId;
    private String password;
    private String nickname;
    private LocalDateTime createTime;
    @JsonProperty("isWithdraw")
    private boolean isWithdraw;
    private Status status;
    private LocalDateTime updateTime;
}