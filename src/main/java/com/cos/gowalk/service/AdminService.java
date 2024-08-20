package com.cos.gowalk.service;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.Notice;
import com.cos.gowalk.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public List<Member> userList() {
        List<Member> usersList = adminRepository.userList();
        List<Member> userArr = new ArrayList<>();
        for (Member member : usersList) {
                if (member.getRn() <= 5){
                    userArr.add(member);
                }
        }
        return userArr;
    }

    public List<Board> boardList() {
        return adminRepository.boardList();
    }

    public List<Notice> noticeList() {
        return adminRepository.noticeList();
    }
}
