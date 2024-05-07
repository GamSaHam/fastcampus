package com.fastcampus.boardserver.dto.request;

import lombok.Getter;
import lombok.Setter;

public class CategoryRequest {
    @Getter
    @Setter
    public static class UpdateCategory {
        private String name;
    }

}
