package com.cos.gowalk.repository;

import com.cos.gowalk.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author   : 윤기환
 * @Class    : UserRepository.java
 * @Desc     : 유저 테이블 SQL 호출 인터페이스
 * */
@Mapper
public interface UserRepository {

    /**
     * @part        : 시큐리티 유저 조회
     * @Author      : 윤기환
     * @Type        : Member
     * */
    Member findByUsername(@Param("username") String username);

    /**
     * @part        : 유저 등록
     * @Author      : 윤기환
     * @Type        : int
     * */
    int saveMember(Member member);

    /**
     * @part        : ID 중복 체크
     * @Author      : 윤기환
     * @Type        : int
     * */
    int idCheck(String memId);

    /**
     * @part        : 일반 유저 조회
     * @Author      : 윤기환
     * @Type        : List<Member>
     * */
    List<Member> findByMember(Long memNum);

    /**
     * @part        : 유저 정보 업데이트
     * @Author      : 윤기환
     * @Type        : int
     * */
    int updateMember(Member member);

    Member userList();
}
