package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;

import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    @Override
    public void register(String userId, CategoryDTO categoryDTO) {
        if(userId == null) {
            log.error("register ERROR: {}", categoryDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "register ERROR 게시글 카테고리 등록 메서드를 확인 해주세요. userId:"+userId);
        }

        try {
            categoryMapper.register(categoryDTO);
        }catch (Exception e) {
            log.error("register 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {

        CategoryDTO findCategoryDTO =  categoryMapper.selectCategory(categoryDTO.getId());

        if(findCategoryDTO == null) {
            log.error("update ERROR: {}", categoryDTO);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "update ERROR 카테고리 항목이 존재하지 않습니다. category:"+categoryDTO);
        }

        try {
            categoryMapper.updateCategory(categoryDTO);
        }catch (Exception e) {
            log.error("update 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        if(id == null) {
            log.error("delete ERROR: {}", id);
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "delete ERROR 게시글 카테고리 삭제 메서드를 확인 해주세요. id:"+id);
        }

        CategoryDTO categoryDTO = categoryMapper.selectCategory(id);

        if(categoryDTO == null) {
            throw new BoardServerException(HttpStatus.BAD_REQUEST, "카테고리 정보가 없습니다. 확인 해 주세요. id:"+ id);
        }

        try {
            categoryMapper.deleteCategory(id);
        }catch (Exception e) {
            log.error("delete 실패");
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }


}
