package com.store.passmeby.PassMeByApi.controller;

import com.store.passmeby.PassMeByApi.dto.request.ProductReqDto;
import com.store.passmeby.PassMeByApi.dto.response.CommonDto;
import com.store.passmeby.PassMeByApi.dto.response.ProductResDto;
import com.store.passmeby.PassMeByApi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    /**
     * 상품 리스트 불러오기
     */
    @GetMapping("/")
    public ResponseEntity<CommonDto<?>> productList(@RequestBody ProductReqDto reqDto) {
        List<ProductResDto> res = productService.getProductList(reqDto);
        return new ResponseEntity<>(CommonDto.ok(res), HttpStatus.OK);
    }


}
