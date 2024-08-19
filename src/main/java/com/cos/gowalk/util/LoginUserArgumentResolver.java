package com.cos.gowalk.util;

import com.cos.gowalk.model.UserSessionDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author   : 윤기환
 * @Class    : LoginUserArgumentResolver.java
 * @Desc     : @LoginUser 파라미터 클래스 타입 컴포넌트 설정 파일
 * */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession session;

    /*
    * @LoginUser 파라미터 클래스 타입 체크(sessionDto) 판단 후 True 반환
    * */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        boolean isUserClass = UserSessionDto.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }
    /*
    * 파라미터에 전달할 객체 생성(세션에서 객체 가져온다.)
    * */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        return session.getAttribute("user");
    }
}
