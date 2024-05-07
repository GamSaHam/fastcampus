package com.fastcampus.boardserver.dto.request;

import lombok.Getter;
import lombok.Setter;
public class PostRequest {

    @Getter
    @Setter
    public static class PostUpdateRequest {
        private String name;
        private String contents;
        private int views;
        private Long categoryId;
        private Long userId;
    }

    @Getter
    @Setter
    public static class PostDeleteRequest {
        private Long postId;
        private Long userId;
    }

    @Getter
    @Setter
    public static class TagRegisterRequest {
        private String name;
    }

    @Getter
    @Setter
    public static class AddTagRequest {
        private Long tagId;
    }

}
