package com.cos.gowalk.service;

import com.cos.gowalk.model.Member;
import com.cos.gowalk.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author   : 윤기환
 * @Class    : UserService.java
 * @Desc     : 유저 로직 처리 서비스
 * */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     *  회원 정보 추가 메서드
     * */
    public boolean save(Member member) {
        member.setAdminCk("ROLE_USER");
        member.setLoginType("Basic");
        // 이유는 패스워드가 암호화가 안되었기 때문에
        String rawPassword = member.getMemPass();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setMemPass(encPassword);

        try {
            int Check = userRepository.saveMember(member);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }
    }
    /*
     *  동일한 아이디 유/무 체크 메서드
     * */
    public int idCheck(String memId) {
        int cnt = userRepository.idCheck(memId);
        return cnt;

    }
    /*
     *  시큐리티 회원 정보 조회 메서드
     * */
    public Member findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    /*
     *  회원 정보 조회 메서드
     * */
    public List<Member> findByMember(Long memNum) {
        List<Member> members = userRepository.findByMember(memNum);


        return members.stream()
                .map(this::replaceNullsWithEmpty)
                .collect(Collectors.toList());
    }
    /*
    *  회원 정보 수정 메서드
    * */
    public boolean updateMember(Member member) {
        // 이유는 패스워드가 암호화가 안되었기 때문에
        String rawPassword = member.getMemPass();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setMemPass(encPassword);
        try {
            int Check = userRepository.updateMember(member);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }
    }
    /*
    *  널 치환 메서드 정의
    * */
    private Member replaceNullsWithEmpty(Member member) {
        if (member.getMemId() == null) {
            member.setMemId("");
        }
        if (member.getMemPass() == null) {
            member.setMemPass("");
        }
        if (member.getMemName() == null) {
            member.setMemName("");
        }
        if (member.getMemEmail() == null) {
            member.setMemEmail("");
        }
        if (member.getMemBirth() == null) {
            member.setMemBirth(0); // 기본값으로 0을 설정
        } else {
            member.setMemBirth(Integer.parseInt(member.getMemBirthAsString()));
        }
        if (member.getMemPhone() == null) {
            member.setMemPhone("");
        }
        if (member.getMemZipCode() == null) {
            member.setMemZipCode(0); // 기본값으로 0을 설정
        } else {
            logger.info("member zipcode = {}" , member.getMemZipCode());
            member.setMemZipCode(Integer.parseInt(member.getMemZipcodeAsString()));
        }
        if (member.getMemAddress1() == null) {
            member.setMemAddress1("");
        }
        if (member.getMemAddress2() == null) {
            member.setMemAddress2("");
        }
        if (member.getMemNickName() == null) {
            member.setMemNickName("");
        }
        if (member.getAdminCk() == null) {
            member.setAdminCk("");
        }

        return member;
    }
}
