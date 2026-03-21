package com.store.passmeby.PassMeByApi.dao;

import com.store.passmeby.PassMeByApi.dao.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDao {

    /**
     * 상품 리스트 조회
     */
    List<ProductVo> selectProductList(@Param("search") ProductVo.Search search);
}
