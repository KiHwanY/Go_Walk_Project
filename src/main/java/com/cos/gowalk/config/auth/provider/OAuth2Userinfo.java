package com.cos.gowalk.config.auth.provider;

/**
 * @Author   : 윤기환
 * @Class    : OAuth2Userinfo.java
 * @Desc     : 소셜 유저 정보 인터페이스
 * */
public interface OAuth2Userinfo {

    String getProviderId(); // naver
    String getGetProvider();// naver
    String getEmail();
    String getName();

}
