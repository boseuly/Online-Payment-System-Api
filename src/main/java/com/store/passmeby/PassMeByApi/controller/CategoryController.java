package com.store.passmeby.PassMeByApi.controller;

import com.store.passmeby.PassMeByApi.dto.response.CategoryResDto;
import com.store.passmeby.PassMeByApi.dto.response.CommonDto;
import com.store.passmeby.PassMeByApi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 전체 조회
     */
    @GetMapping("")
    public ResponseEntity<CommonDto<?>> getCategoryAll() {
        List<CategoryResDto> res = categoryService.selectCategoryAll();

        return new ResponseEntity<>(CommonDto.ok(res), HttpStatus.OK);

    }

    /**
     * 카테고리 단건 조회
     */
    @GetMapping("/{categoryCode}")
    public ResponseEntity<CommonDto<?>> getCategoryByCategoryCode(@PathVariable(required = true) String categoryCode) {
        CategoryResDto res = categoryService.selectCategoryByCategoryCode(categoryCode);

        return new ResponseEntity<>(CommonDto.ok(res), HttpStatus.OK);

    }
}
