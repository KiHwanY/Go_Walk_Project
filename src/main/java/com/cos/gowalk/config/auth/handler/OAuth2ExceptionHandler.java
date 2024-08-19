package com.cos.gowalk.config.auth.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * @Author   : 윤기환
 * @Class    : CustomAuthFailureHandler.java
 * @Desc     : 시큐리티 소셜 로그인 실패 핸들러
 * */
@ControllerAdvice
public class OAuth2ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2ExceptionHandler.class);

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public String handleOAuth2AuthenticationException(OAuth2AuthenticationException e,
                                                      Model model) {
        model.addAttribute("error", "true");
        model.addAttribute("exception", e.getMessage());
        return "redirect:/loginForm";
    }
}