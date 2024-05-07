package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.controller.PostSearchController;
import com.fastcampus.boardserver.dto.PostDTO;

import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchController.PostSearchRequest postSearchRequest);

    List<PostDTO> getPostsByTagName(String name);
}
