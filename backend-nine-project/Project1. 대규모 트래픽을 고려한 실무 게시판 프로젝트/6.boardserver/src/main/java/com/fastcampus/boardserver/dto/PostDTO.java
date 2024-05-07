package com.fastcampus.boardserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String name;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    private String contents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int views;
    private Long categoryId;
    private Long userId;
    private List<TagDTO> tagList;
}
