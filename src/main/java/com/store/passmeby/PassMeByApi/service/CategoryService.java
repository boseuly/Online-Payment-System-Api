package com.store.passmeby.PassMeByApi.service;

import com.store.passmeby.PassMeByApi.dao.CategoryDao;
import com.store.passmeby.PassMeByApi.dao.vo.CategoryVo;
import com.store.passmeby.PassMeByApi.dto.response.CategoryResDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ModelMapper modelMapper;

    /**
     * 카테고리 전체 조회
     */
    public List<CategoryResDto> selectCategoryAll() {
        List<CategoryVo> voList = categoryDao.selectCategoryAll();
        return voList.stream().map(vo -> modelMapper.map(vo, CategoryResDto.class)).toList();
    }


}
