package com.fastcampus.boardserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Long postId;
    private String contents;
    private Long commentId;

    private Long userId;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public CommentDTO(Long postId, String contents) {
        this.postId = postId;
        this.contents = contents;
    }
}
