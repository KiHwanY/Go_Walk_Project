package com.cos.gowalk.model;

import lombok.Getter;

import java.io.Serializable;
/**
 * @Author   : 윤기환
 * @Class    : UserSessionDto.java
 * @Desc     : 인증된 사용자 정보 저장 세션 클래스
 * */
@Getter
public class UserSessionDto implements Serializable {

        private Long idx;
        private String username;
        private String password;
        private String nickname;
        private String role;

        private String loginType;

        public UserSessionDto() {}

        public UserSessionDto(Member user){
            this.idx = user.getMemNum();
            this.username = user.getMemId();
            this.password = user.getMemPass();
            this.nickname = user.getMemNickName();
            this.role = user.getAdminCk();
            this.loginType = user.getLoginType();

        }

}
