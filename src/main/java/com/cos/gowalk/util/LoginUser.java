package com.cos.gowalk.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author   : 윤기환
 * @Class    : LoginUser.java
 * @Desc     : @interface 생성될 수 있는 위치 지정 및 런타임 보존 파일
 * */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
