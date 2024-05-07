package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.PostRequest;

import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserProfileMapper userProfileMapper;
    private final PostMapper postMapper;
    @Override
    public void register(String userId, PostDTO postDTO) {
        UserDTO userDTO = userProfileMapper.selectUserProfile(userId);
        if(userDTO == null) {
            log.error("register ERROR: {}", postDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "회원 정보가 존재하지 않습니다.");
        }

        postDTO.setUserId(userDTO.getId());

        try {
            postMapper.register(postDTO);
        }catch (Exception e) {
            log.error("register 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
    @Override
    public List<PostDTO> getMyPosts(Long userId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(userId);

        for (PostDTO postDTO : postDTOList) {
            List<TagDTO> tagList = postMapper.selectTags(postDTO.getId());
            postDTO.setTagList(tagList);
        }

        return postDTOList;
    }
    @Override
    public int updatePost(PostDTO postDTO) {

        if(postDTO == null || postDTO.getId() == null) {
            log.error("updatePost ERROR: {}", postDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "게시글 정보가 없습니다. 확인 해 주세요");
        }

        PostDTO findPost = postMapper.selectPost(postDTO.getId());

        if(findPost == null) {
            log.error("updatePost ERROR: {}", postDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "게시글 정보가 없습니다. 확인 해 주세요");
        }

        int result = 0;
        try {
            result = postMapper.updatePost(postDTO);
        }catch (Exception e) {
            log.error("updatePost 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return result;
    }
    @Override
    public int deletePost(Long postId) {
        if(postId == null) {
            log.error("deletePost ERROR");
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "게시글 정보가 없습니다. 확인 해 주세요");
        }

        PostDTO findPost = postMapper.selectPost(postId);

        if(findPost == null) {
            log.error("deletePost ERROR: {}", postId);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "게시글 정보가 없습니다. 확인 해 주세요");
        }

        int result = 0;

        try {
            result = postMapper.deletePost(postId);

            // 게시글 삭제 시 게시글에 등록된 태그도 삭제
            postMapper.deletePosttagByPostID(postId);

        }catch (Exception e) {
            log.error("deletePost 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }



        return result;
    }
    @Override
    public void registerComment(String userId, CommentDTO commentDTO) {
        UserDTO userDTO = userProfileMapper.selectUserProfile(userId);
        if(userDTO == null) {
            log.error("registerComment ERROR: {}", commentDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "회원 정보가 존재하지 않습니다.");
        }

        commentDTO.setUserId(userDTO.getId());

        try {
            postMapper.insertComment(commentDTO);
        }catch (Exception e) {
            log.error("registerComment 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
    @Override
    public void saveComment(String userId, Long commentId, CommentDTO commentDTO) {
        UserDTO userDTO = userProfileMapper.selectUserProfile(userId);
        if(userDTO == null) {
            log.error("saveComment ERROR: {}", commentDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "회원 정보가 존재하지 않습니다.");
        }

        commentDTO.setUserId(userDTO.getId());
        commentDTO.setId(commentId);

        try {
            postMapper.updateComment(commentDTO);
        }catch (Exception e) {
            log.error("saveComment 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        CommentDTO commentDTO = postMapper.selectComment(commentId);
        if(commentDTO == null) {
            log.error("deleteComment ERROR: {}", commentId);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "댓글 정보가 없습니다. 확인 해 주세요");
        }

        try {
            postMapper.deleteComment(commentId);
        }catch (Exception e) {
            log.error("deleteComment 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
    @Override
    public void registerTag(String userId, PostRequest.TagRegisterRequest request) {
        UserDTO userDTO = userProfileMapper.selectUserProfile(userId);
        if(userDTO == null) {
            log.error("registerTag ERROR: {}", request);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "회원 정보가 존재하지 않습니다.");
        }

        try {
            postMapper.registerTag(new TagDTO(request.getName()));
        }catch (Exception e) {
            log.error("registerTag 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
    @Override
    public void addTag(Long postId, Long tagId) {
        PostDTO postDTO = postMapper.selectPost(postId);
        if(postDTO == null) {
            log.error("addTag ERROR: {}", postId);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "게시글 정보가 없습니다. 확인 해 주세요");
        }

        try {
            postMapper.registerPosttag(postId, tagId);
        }catch (Exception e) {
            log.error("addTag 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void removeTag(Long postId, Long tagId) {
        PostDTO postDTO = postMapper.selectPost(postId);
        if(postDTO == null) {
            log.error("removeTag ERROR: {}", postId);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "게시글 정보가 없습니다. 확인 해 주세요");
        }

        TagDTO tagDTO = postMapper.selectTag(tagId);

        if(tagDTO == null) {
            log.error("removeTag ERROR: {}", tagId);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "태그 정보가 없습니다. 확인 해 주세요");
        }

        try {
            postMapper.deletePosttag(postId, tagId);
        }catch (Exception e) {
            log.error("removeTag 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }


}
