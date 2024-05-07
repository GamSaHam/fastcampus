package com.fastcampus.boardserver.dto.response;

import com.fastcampus.boardserver.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponse {
    private List<PostDTO> postList;
}
