package com.store.passmeby.PassMeByApi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonDto<T> {

    private boolean success;
    private String code;        // OK,
    private String message;
    private T data;


    private CommonDto(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    // ---------- 성공 응답 ----------

    /**
     * static 으로 사용한 이유는 사용성 때문
     */
    public static <T> CommonDto<T> ok(T data) {
        return new CommonDto<>(true, "success", null, data);
    }

    public static <T> CommonDto<T> ok(String message, T data) {
        return new CommonDto<>(true, "success", message, data);
    }

    // data 없는 성공 (예: 데이터 삭제 성공 시)
    public static CommonDto<Void> ok() {
        return new CommonDto<>(true, "success", null, null);
    }

    // ---------- 실패 응답 ----------
    public static <T> CommonDto<T> fail( String message) {
        return new CommonDto<>(false, "fail", message, null);
    }
}
