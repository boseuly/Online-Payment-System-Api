package com.store.passmeby.PassMeByApi.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 매개변수 생성자
public class CustomerResDto {

    /**
     * 회원 아이디
     */
    private String customerId;

    /**
     * 이메일
     */
    private String email;

    /**
     * 패스워드
     */
    private String password;

    /**
     * 회원명
     */
    private String customerName;

    /**
     * 권한
     */
    private String role;

}
