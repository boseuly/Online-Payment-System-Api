package com.store.passmeby.PassMeByApi.dao.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * 회원 Vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVo {

    /**
     * 회원 아이디
     */
    private String customerId;

    /**
     * 이메일
     */
    private String email;

    /**
     * 핸드폰 번호
     */
    private String phoneNumber;

    /**
     * 패스워드
     */
    private String password;

    /**
     * 회원명
     */
    private String customerName;



}