package com.cos.gowalk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * @Author   : 윤기환
 * @Class    : Board.java
 * @Desc     : 리뷰 테이블 모델
 * */
@Getter
@Setter
@NoArgsConstructor
public class Board {

    private long rNum;
    private long memNum;
    private long lNum;
    private String title;
    private String content;
    private LocalDateTime createDt;
    private long readCount;
    private Integer next;
    private Integer prev;
    private LocalDateTime updateDt;

    private Local local;
    private Member member;

    private String updateDtResult;

    private String reviewYn;

}
