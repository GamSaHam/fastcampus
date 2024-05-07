package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.PostRequest;
import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.service.PostService;
import com.fastcampus.boardserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;
    private final PostService postService;
    @PostMapping
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String userId, @RequestBody PostDTO postDTO) {
        postService.register(userId, postDTO);

        CommonResponse<PostDTO> response = new CommonResponse<>(HttpStatus.CREATED, "SUCCESS", "registerPost", postDTO);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/mypost")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> getMyPosts(String userId) {
        UserDTO memberInfo = userService.getUserInfo(userId);
        List<PostDTO> myPosts = postService.getMyPosts(memberInfo.getId());
        CommonResponse<List<PostDTO>> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "getMyPosts", myPosts);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> updatePost(String userId, @PathVariable Long postId, @RequestBody PostRequest.PostUpdateRequest request) {

        UserDTO memberInfo = userService.getUserInfo(userId);

        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(request.getName())
                .contents(request.getContents())
                .views(request.getViews())
                .categoryId(request.getCategoryId())
                .userId(memberInfo.getId())
                .build();

        postService.updatePost(postDTO);

        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePost", postDTO);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse> deletePost(String userId, @PathVariable Long postId) {
        postService.deletePost(postId);

        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePost", null);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/comments")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerComment(String userId, @RequestBody CommentDTO commentDTO) {
        postService.registerComment(userId, commentDTO);

        CommonResponse<CommentDTO> response = new CommonResponse<>(HttpStatus.CREATED, "SUCCESS", "registerComment", commentDTO);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> saveComment(String userId, @PathVariable("commentId") Long commentId, @RequestBody CommentDTO commentDTO) {
        postService.saveComment(userId, commentId, commentDTO);

        CommonResponse<CommentDTO> response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "modifyComment", commentDTO);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse> deleteComment(String userId, @PathVariable Long commentId) {
        postService.deleteComment(commentId);

        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteComment", null);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/tags")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostRequest.TagRegisterRequest>> registerTag(String userId, @RequestBody PostRequest.TagRegisterRequest request) {
        postService.registerTag(userId, request);

        CommonResponse response = new CommonResponse<>(HttpStatus.CREATED, "SUCCESS", "registerTag", request);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{postId}/tags")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostRequest.AddTagRequest>> addTag(String userId, @PathVariable Long postId, @RequestBody PostRequest.AddTagRequest request) {
        postService.addTag(postId, request.getTagId());

        CommonResponse response = new CommonResponse<>(HttpStatus.CREATED, "SUCCESS", "addTag", request);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{postId}/tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostRequest.AddTagRequest>> removeTag(String userId, @PathVariable("postId") Long postId, @PathVariable("tagId") Long tagId) {
        postService.removeTag(postId, tagId);

        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteTag", null);

        return ResponseEntity.ok(response);
    }
}
