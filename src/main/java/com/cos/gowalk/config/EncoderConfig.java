package com.cos.gowalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author   : 윤기환
 * @Class    : EncoderConfig.java
 * @Desc     : 비밀번호 암호화 설정 파일
 * */
@Configuration
public class EncoderConfig {
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
