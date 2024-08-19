package com.cos.gowalk.repository;

import com.cos.gowalk.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author   : 윤기환
 * @Class    : NoticeRepository.java
 * @Desc     : 공지사항 테이블 SQL 호출 인터페이스
 * */
@Mapper
public interface NoticeRepository {

    /**
     * @part        : 공지사항 리스트 조회
     * @Author      : 윤기환
     * @Type        : List<Notice>
     * */
    List<Notice> findByReviewList(HashMap<String, Object> data);

    /**
     * @part        : 공지사항 총 게시글 수 조회
     * @Author      : 윤기환
     * @Type        : int
     * */
    int total(HashMap<String, Object> data);

    /**
     * @part        : 공지사항 등록
     * @Author      : 윤기환
     * @Type        : int
     * */
    int insertNotice(Notice notice);

    /**
     * @part        : 조회 수 업데이트
     * @Author      : 윤기환
     * @Type        : int
     * */
    void readCount(long noNum);

    /**
     * @part        : 특정 게시글 조회
     * @Author      : 윤기환
     * @Type        : Board
     * */
    Notice detail(long noNum);

    /**
     * @part        : 공지사항 수정
     * @Author      : 윤기환
     * @Type        : int
     * */
    int editNotice(Notice notice);

    /**
     * @part        : 공지사항 삭제
     * @Author      : 윤기환
     * @Type        : int
     * */
    int deleteNotice(Notice notice);

}
