package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostRequest;

import java.util.List;

public interface PostService {
    void register(String userId, PostDTO postDTO);
    List<PostDTO> getMyPosts(Long userId);
    int updatePost(PostDTO postDTO);
    int deletePost(Long postId);
    void registerComment(String userId, CommentDTO commentDTO);
    void saveComment(String userId, Long commentId, CommentDTO commentDTO);
    void deleteComment(Long commentId);
    void registerTag(String userId, PostRequest.TagRegisterRequest request);
    void addTag(Long postId, Long tagId);
    void removeTag(Long postId, Long tagId);
}
