package com.store.passmeby.PassMeByApi.controller;

import com.store.passmeby.PassMeByApi.dto.request.CustomerReqDto;
import com.store.passmeby.PassMeByApi.dto.response.CommonDto;
import com.store.passmeby.PassMeByApi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<CommonDto<?>> customerAdd(@RequestBody CustomerReqDto reqDto) {
        int result = customerService.addCustomer(reqDto);
        if (result < 0) {
            return new ResponseEntity<>(CommonDto.fail("실패하였습니다."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CommonDto.ok(), HttpStatus.OK);

    }
    /**
     * 아이디 중복 확인
     */
    @GetMapping("/available")
    public ResponseEntity<CommonDto<?>> checkAvailable(@RequestParam(required = false, defaultValue = "") String customerId,
                                                       @RequestParam(required = false, defaultValue = "") String email) {

        boolean available = false;
        // 둘 중 하나라도 값이 있다면 로직 태우기
        if (!"".equals(customerId) || !"".equals(email)) {
            available = customerService.checkAvailableIdOrEmail(customerId, email);
        }

        return new ResponseEntity<>(CommonDto.ok(null, available), HttpStatus.OK);

    }



}
