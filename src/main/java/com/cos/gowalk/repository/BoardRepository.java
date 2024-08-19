package com.cos.gowalk.repository;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.model.Local;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
/**
 * @Author   : 윤기환
 * @Class    : BoardRepository.java
 * @Desc     : 리뷰 테이블 SQL 호출 인터페이스
 * */
@Mapper
public interface BoardRepository {

    /**
     * @part        : 리뷰 리스트 조회
     * @Author      : 윤기환
     * @Type        : List<Board>
     * */
    List<Board> findByReviewList(HashMap<String, Object> data);

    /**
     * @part        : 리뷰 총 게시글 수 조회
     * @Author      : 윤기환
     * @Type        : int
     * */
    int total(HashMap<String, Object> data);

    /**
     * @part        : 리뷰 등록
     * @Author      : 윤기환
     * @Type        : int
     * */
    int insertReview(Board board);

    /**
     * @part        : 조회 수 업데이트
     * @Author      : 윤기환
     * @Type        : int
     * */
    void readCount(long rNum);

    /**
     * @part        : 특정 게시글 조회
     * @Author      : 윤기환
     * @Type        : Board
     * */
    Board detail(long lNum);

    /**
     * @part        : 게시글 수정
     * @Author      : 윤기환
     * @Type        : int
     * */
    int editReview(Board board);

    /**
     * @part        : 게시글 삭제
     * @Author      : 윤기환
     * @Type        : int
     * */
    int deleteReview(Board board);
}
