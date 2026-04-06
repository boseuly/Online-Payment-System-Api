package com.store.passmeby.PassMeByApi.dao;

import com.store.passmeby.PassMeByApi.dao.vo.CategoryVo;
import com.store.passmeby.PassMeByApi.dto.response.CategoryResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao  {

    /**
     * 카테고리 전체 조회
     */
    List<CategoryVo> selectCategoryAll();

    /**
     * 카테고리 단건 조회
     */
    CategoryResDto selectCategoryByCategoryCode(String categoryCode);
}
