package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.controller.PostSearchController;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {
    private final PostSearchMapper postSearchMapper;


    @Async
    @Cacheable(value="getPosts", key="'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId() ")
    @Override
    public List<PostDTO> getPosts(PostSearchController.PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = postSearchMapper.selectPosts(postSearchRequest);

        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostsByTagName(String name) {
        List<PostDTO> postDTOList = postSearchMapper.selectPostsByTagName(name);

        return postDTOList;
    }

}
