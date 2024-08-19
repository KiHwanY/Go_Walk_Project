package com.cos.gowalk.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * @Author   : 윤기환
 * @Class    : Member.java
 * @Desc     : 유저 테이블 모델
 * */
@Getter
@Setter
@NoArgsConstructor
public class Member {
    private Long memNum;
    private String memId;
    private String memPass;
    private String memName;

    private String memEmail;

    private Integer memBirth;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private String memPhone;
    private Integer memZipCode;
    private String memAddress1;
    private String memAddress2;

    private String memNickName;
    private String adminCk;

    private String username;

    private String provider;
    private String providerId;

    private String loginType;

    public String getMemBirthAsString() {
        return memBirth == null ? "" : memBirth.toString();
    }

    public String getMemZipcodeAsString() {
        return memZipCode == null ? "" : memZipCode.toString();
    }

    @Builder
    public Member(Long memNum, String memId, String memPass, String memName,String memEmail, Integer memBirth,
                  LocalDateTime createDt, LocalDateTime updateDt, String memPhone, Integer memZipCode,
                  String memAddress1, String memAddress2, String memNickName,
                  String adminCk, String provider, String providerId,String loginType) {
        this.memNum = memNum;
        this.memId = memId;
        this.memPass = memPass;
        this.memEmail = memEmail;
        this.memName = memName;
        this.memBirth = memBirth;
        this.createDt = createDt;
        this.updateDt = updateDt;
        this.memPhone = memPhone;
        this.memZipCode = memZipCode;
        this.memAddress1 = memAddress1;
        this.memAddress2 = memAddress2;
        this.memNickName = memNickName;
        this.adminCk = adminCk;
        this.provider = provider;
        this.providerId = providerId;
        this.loginType = loginType;
    }
}
