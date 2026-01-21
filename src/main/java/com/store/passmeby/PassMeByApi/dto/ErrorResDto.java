package com.store.passmeby.PassMeByApi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 예외 발생 시 사용자 응답으로 줄 Response 객체
 */
@Data
@RequiredArgsConstructor // 매개변수 생성자
public class ErrorResDto {
    private final int status;   // 상태(불변값)
    private final String message;
    private final LocalDateTime time;
    private String stockTrace;
    private List<ValidationError> validErrors;


    @Data
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(validErrors)) {
            validErrors = new ArrayList<>();
        }
        validErrors.add(new ValidationError(field, message));
    }


}
