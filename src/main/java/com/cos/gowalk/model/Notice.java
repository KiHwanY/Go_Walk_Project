package com.cos.gowalk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * @Author   : 윤기환
 * @Class    : Notice.java
 * @Desc     : 공지사항 테이블 모델
 * */
@Getter
@Setter
@NoArgsConstructor
public class Notice {

    private long rn;
    private long noNum;
    private long memNum;

    private String title;

    private String content;

    private long readCount;

    private Integer next;
    private Integer prev;

    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private Member member;

    private String updateDtResult;

    private String noticeYn;
}
