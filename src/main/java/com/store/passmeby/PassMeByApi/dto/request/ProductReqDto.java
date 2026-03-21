package com.store.passmeby.PassMeByApi.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDto {
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
