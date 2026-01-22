package com.store.passmeby.PassMeByApi.dao;
import com.store.passmeby.PassMeByApi.dao.vo.CustomerVo;
import com.store.passmeby.PassMeByApi.dao.vo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;


@Mapper
public interface CustomerDao {
    // security 를 위한 회원 조회
    Users findByUsername(@Param("email") String email);
    Optional<Users> findByRefreshToken(@Param("refreshToke") String refreshToke);
    Optional<Users> findByEmail(@Param("email") String email);

    /**
     * 회원 아이디 중복 확인
     */
    int selectByCustomerId(@Param("id")String id);

    /**
     * 회원 이메일 중복 확인
     */
    int selectByCustomerEmail(@Param("email")String email);

    /**
     * 회원 정보 등록
     */
    int insertCustomer(@Param("vo")CustomerVo vo);


    /**
     * refreshToken 저장
     */
    void insertRefreshToken(@Param("refreshToken") String refreshToken,
                            @Param("email") String email);

    /**
     * refreshToken 삭제
     */
    void deleteRefreshToken(@Param("refreshToken") String refreshToken);
}
