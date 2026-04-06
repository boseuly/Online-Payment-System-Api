package com.store.passmeby.PassMeByApi.service;

import com.store.passmeby.PassMeByApi.dao.ProductDao;
import com.store.passmeby.PassMeByApi.dao.vo.ProductVo;
import com.store.passmeby.PassMeByApi.dto.response.ProductResDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    /**
     * 상품 리스트 조회
     */
    public List<ProductResDto> getProductList(int currentPage, int pageSize, String categoryCode) {
        ProductVo.Search search = new ProductVo.Search().builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .categoryCode(categoryCode)
                .build();
        List<ProductVo> voList = productDao.selectProductList(search);

        return voList.stream().map(vo -> modelMapper.map(vo, ProductResDto.class)).toList();

    }

    /**
     * 상품 단건 불러오기
     */
    public ProductResDto selectProductByProductCode(String productCode) {
        ProductVo vo = productDao.selectProductByProductCode(productCode);
        return modelMapper.map(vo, ProductResDto.class);
    }
}
