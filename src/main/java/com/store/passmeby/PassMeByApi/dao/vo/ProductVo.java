package com.store.passmeby.PassMeByApi.dao.vo;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVo {
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

    private Search search;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Search {
        /**
         * 정렬
         */
        private String sort;

        /**
         * 현재 페이지
         */
        private int currentPage;

        /**
         * 페이지당 데이터 개수(default 20)
         */
        private int pageSize;

        /**
         * 시작 위치(currentPage * pageSize)
         */
        private int startPage;

        private String categoryCode;
    }


}
