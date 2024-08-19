package com.cos.gowalk.repository;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminRepository {

    List<Member> userList();

    List<Board> boardList();

    List<Notice> noticeList();
}
