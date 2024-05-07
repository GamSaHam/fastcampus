

package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.controller.PostSearchController;
import com.fastcampus.boardserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    List<PostDTO> selectPosts(PostSearchController.PostSearchRequest postSearchRequest);

    List<PostDTO> selectPostsByTagName(@Param("name") String name);
}