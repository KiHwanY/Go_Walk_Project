package com.cos.gowalk.repository;

import com.cos.gowalk.model.Comments;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @Author   : 윤기환
 * @Class    : CommentsRepository.java
 * @Desc     : 댓글 테이블 SQL 호출 인터페이스
 * */
@Mapper
public interface CommentsRepository {

    /**
     * @part        : 댓글 리스트 조회
     * @Author      : 윤기환
     * @Type        : List<Comments>
     * */
    List<Comments> commentsList(Comments comments);

    /**
     * @part        : 댓글 등록
     * @Author      : 윤기환
     * @Type        : int
     * */
    int insertComments(Comments comments);

    /**
     * @part        : 댓글 수정
     * @Author      : 윤기환
     * @Type        : int
     * */
    int editComments(Comments comments);

    /**
     * @part        : 댓글 삭제
     * @Author      : 윤기환
     * @Type        : int
     * */
    int deleteComments(Comments comments);
}
