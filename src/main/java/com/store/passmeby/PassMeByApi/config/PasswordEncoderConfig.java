package com.store.passmeby.PassMeByApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
public class PasswordEncoderConfig {

    // bcrypt는 함호화를 할 때 해시 알고리즘인 SHA-256을 사용
    // 해시 알고리즘의 태표적인 특징 : 암호화는 가능하지만 복호화는 불가능하다.
    // 따라서 비밀번호를 잃어버리면 재설정 필수
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}