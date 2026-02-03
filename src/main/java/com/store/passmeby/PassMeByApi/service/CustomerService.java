package com.store.passmeby.PassMeByApi.service;

import com.store.passmeby.PassMeByApi.dao.CustomerDao;
import com.store.passmeby.PassMeByApi.dao.vo.CustomerVo;
import com.store.passmeby.PassMeByApi.dao.vo.Users;
import com.store.passmeby.PassMeByApi.dto.request.CustomerReqDto;
import com.store.passmeby.PassMeByApi.dto.response.CustomerResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class CustomerService {

    // ModelMapperConfig 대신 작성
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public int addCustomer(CustomerReqDto reqDto) {
        // 이메일 검사 전에 실행하긴 하지만 다시 한 번 실행
        Users checkDuplication = customerDao.findByUsername(reqDto.getEmail());
        if (checkDuplication != null) {
            return -1;
        }
        // 회원 패스워드 BCrypt 형식으로 변환 후 저장
        String encodedPassword = passwordEncoder.encode(reqDto.getPassword());
        reqDto.setPassword(encodedPassword);

        // 회원이메일이 존재하지 않으면 바로 로그인 등록
        customerDao.insertCustomer(CustomerVo.builder()
                        .customerId(reqDto.getCustomerId())
                        .customerName(reqDto.getCustomerName())
                        .email(reqDto.getEmail())
                        .password(reqDto.getPassword())
                        .phoneNumber(reqDto.getPhoneNumber())
                .build());

        return 0;
    }

    /**
     * 아이디 중복 확인
     */
    public boolean checkAvailableIdOrEmail(String customerId, String email) {
        int result = 1;
        if (!"".equals(customerId) && "".equals(email)) {
            result = customerDao.selectByCustomerId(customerId);
        } else  {
            result = customerDao.selectByCustomerEmail(email);
        }
        return result <= 0;
    }

}
