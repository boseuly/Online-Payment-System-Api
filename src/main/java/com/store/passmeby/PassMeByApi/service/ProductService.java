package com.store.passmeby.PassMeByApi.service;

import com.store.passmeby.PassMeByApi.dao.ProductDao;
import com.store.passmeby.PassMeByApi.dao.vo.ProductVo;
import com.store.passmeby.PassMeByApi.dto.request.ProductReqDto;
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
    public List<ProductResDto> getProductList(ProductReqDto reqDto) {
        ProductVo.Search search = modelMapper.map(reqDto, ProductVo.Search.class);
        List<ProductVo> voList = productDao.selectProductList(search);

        return voList.stream().map(vo -> modelMapper.map(vo, ProductResDto.class)).toList();

    }
}
