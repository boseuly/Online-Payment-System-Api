package com.store.passmeby.PassMeByApi.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResDto {
    /**
     * 상품코드
     */
    private String productCode;
    /**
     * 상품명
     */
    private String productName;
    /**
     * 카테고리코드
     */
    private String categoryCode;
    /**
     * 상품가격
     */
    private long productPrice;
    /**
     * 사용여부
     */
    private String useYn;
    /**
     * 상품 상세 이미지(임시 - 나중에 firebase storage 사용)
     */
    private String productImagePath;
}
