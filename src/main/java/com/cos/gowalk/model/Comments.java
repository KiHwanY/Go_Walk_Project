package com.cos.gowalk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * @Author   : 윤기환
 * @Class    : Comments.java
 * @Desc     : 댓글 테이블 모델
 * */
@Getter
@Setter
@NoArgsConstructor
public class Comments {

    private long cNum;

    private long rNum;

    private long memNum;

    private String content;

    private String comYn;

    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private Local local;
    private Member member;

    private String updateDtResult;

    private boolean userCheck;




}
