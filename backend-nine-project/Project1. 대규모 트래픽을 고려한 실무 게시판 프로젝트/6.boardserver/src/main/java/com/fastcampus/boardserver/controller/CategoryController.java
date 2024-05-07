package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.dto.request.CategoryRequest;
import com.fastcampus.boardserver.exception.BoardServerException;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public ResponseEntity<?> registerCategory(String userId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(userId, categoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public ResponseEntity<?>  updateCategory(String userId, @PathVariable("id") Long id, @RequestBody CategoryRequest.UpdateCategory request) {

        CategoryDTO categoryDTO = new CategoryDTO(id, request.getName(), CategoryDTO.SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public ResponseEntity<?> deleteCategory(String userId, @PathVariable("id") Long id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
