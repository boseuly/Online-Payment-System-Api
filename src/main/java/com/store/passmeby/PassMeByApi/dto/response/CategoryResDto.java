package com.store.passmeby.PassMeByApi.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResDto {

    /**
     * 카테고리 코드
     */
    private String categoryCode;

    /**
     * 카테고리명
     */
    private String categoryName;

    /**
     * 카테고리값
     */
    private String categoryValue;

    /**
     * 카테고리 부모 코드
     */
    private String parentCode;

    /**
     * 카테고리 정렬
     */
    private int sort;

    /**
     * 카테고리 사용여부
     */
    private String useYn;
}
