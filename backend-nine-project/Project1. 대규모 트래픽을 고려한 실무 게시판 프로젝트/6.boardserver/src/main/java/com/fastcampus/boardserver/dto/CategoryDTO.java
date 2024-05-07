package com.fastcampus.boardserver.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CategoryDTO {

    public enum SortStatus {
        CATEGORIES, NEWEST, OLDEST
    }

    private Long id;
    private String name;
    private SortStatus sortStatus;
    private int searchCount;
    private int pagingStartOffset; // offset 구간

}
