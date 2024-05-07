package com.fastcampus.boardserver.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private String id;
    private String name;
    public TagDTO(String name) {
        this.name = name;
    }

}
