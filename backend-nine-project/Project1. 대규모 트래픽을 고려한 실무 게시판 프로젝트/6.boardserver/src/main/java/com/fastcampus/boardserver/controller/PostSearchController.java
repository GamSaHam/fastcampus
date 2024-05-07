package com.fastcampus.boardserver.controller;


import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class PostSearchController {
    private final PostSearchService postSearchService;

    @PostMapping
    public PostSearchResponse searchPost(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDTO> posts = postSearchService.getPosts(postSearchRequest);
        return new PostSearchResponse(posts);
    }

    @PostMapping("/tagName")
    public PostSearchResponse searchPostByTagName(@RequestBody PostSearchTagNameRequest request) {
        List<PostDTO> posts = postSearchService.getPostsByTagName(request.getName());

        return new PostSearchResponse(posts);
    }

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> postDTOList;
    }

    @Builder
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostSearchRequest {
        private int id;
        private String name;
        private String contents;
        private int views;
        private Long categoryId;
        private Long userId;
        private CategoryDTO.SortStatus sortStatus;
    }


    @Getter
    public static class PostSearchTagNameRequest {

        private String name;
    }





}
