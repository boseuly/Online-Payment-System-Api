package com.store.passmeby.PassMeByApi.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {
    /**
     * 정렬
     */
    private String sort;
    private String categoryCode;

}
