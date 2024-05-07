

package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    int register(PostDTO postDTO);
    List<PostDTO> selectMyPosts(Long userId);
    int updatePost(PostDTO postDTO);
    int deletePost(Long id);
    PostDTO selectPost(Long id);
    int insertComment(CommentDTO commentDTO);
    void updateComment(CommentDTO commentDTO);
    CommentDTO selectComment(Long commentId);
    void deleteComment(Long commentId);
    Long registerTag(TagDTO tagDTO);
    void registerPosttag(@Param("postId") Long postId,@Param("tagId") Long tagId);
    TagDTO selectTag(@Param("tagId")  Long tagId);
    void deletePosttag(Long postId, Long tagId);
    void deletePosttagByPostID(Long postId);

    List<TagDTO> selectTags(Long id);
}