package com.cos.gowalk.repository;

import com.cos.gowalk.model.Local;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @Author   : 윤기환
 * @Class    : LocalRepository.java
 * @Desc     : 지역 테이블 SQL 호출 인터페이스
 * */
@Mapper
public interface LocalRepository {

    /**
     * @part        : 지역 리스트 조회
     * @Author      : 윤기환
     * @Type        : List<Local>
     * */
    List<Local> localList();

    /**
     * @part        : 특정 지역 조회
     * @Author      : 윤기환
     * @Type        : Local
     * */
    Local selectLocalList(Integer lNum);
}
