package com.cos.gowalk.config.auth;

import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author   : 윤기환
 * @Class    : PrincipalDetailsService.java
 * @Desc     : 시큐리티 사용자 정보 조회 파일
 * */

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    //시큐리티 설정에서 loginProcessingUrl("/login");
    // /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행

    private static final Logger logger = LoggerFactory.getLogger(PrincipalDetailsService.class);
    private final UserRepository userRepository;

    private final HttpSession session;

    //시큐리티 session(내부 Authentication(내부 UserDetails))
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername 진입 완료 했습니다.");
        logger.info("username = " + username);

        Member userEntity = userRepository.findByUsername(username);

        logger.info("userEntity = " + userEntity);

        if (userEntity != null) {
            session.setAttribute("user", new UserSessionDto(userEntity));
            UserSessionDto userSessionDto = new UserSessionDto(userEntity);
            logger.info("username = " + userSessionDto.getUsername());
            logger.info("idx = " + userSessionDto.getIdx());
            logger.info("pwd = " + userSessionDto.getPassword());
            logger.info("nickName = " + userSessionDto.getNickname());
            logger.info("role = " + userSessionDto.getRole());

            return new PrincipalDetails(userEntity); // username이 있으면?
        } else {
            logger.info("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username); // 없으면 null
        }
    }
}


