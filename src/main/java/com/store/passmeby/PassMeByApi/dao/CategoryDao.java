package com.store.passmeby.PassMeByApi.dao;

import com.store.passmeby.PassMeByApi.dao.vo.CategoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao  {

    List<CategoryVo> selectCategoryAll();

}
