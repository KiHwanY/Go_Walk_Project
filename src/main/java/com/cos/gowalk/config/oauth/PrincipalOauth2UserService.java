package com.cos.gowalk.config.oauth;

import com.cos.gowalk.config.auth.PrincipalDetails;
import com.cos.gowalk.config.auth.provider.*;
import com.cos.gowalk.controller.IndexController;
import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @Author   : 윤기환
 * @Class    : PrincipalOauth2UserService.java
 * @Desc     : 소셜 로그인 서비스 파일
 * */
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(PrincipalOauth2UserService.class);
    @Autowired
    private  HttpSession session;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    // 네이버로부터 받은 userRequest 데이터에 대한 후처리되는 함수이다.
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override // 후처리 기능
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("loadUser called");
        logger.info("getClientRegistration = " + userRequest.getClientRegistration());
        logger.info("getAccessToken = " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User;
        try {
            oAuth2User = super.loadUser(userRequest);
        } catch (OAuth2AuthenticationException e) {
            logger.info("Error loading user: " + e.getError().getDescription());
            throw new OAuth2AuthenticationException(e.getError(), e.getMessage());
        }
        logger.info("getAttributes = " + oAuth2User.getAttributes());

        OAuth2Userinfo oAuth2Userinfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            logger.info("네이버 로그인 요청");
            oAuth2Userinfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }  else {
            logger.info("우리는 네이버만 지원해요.");
            throw new OAuth2AuthenticationException(new OAuth2Error("unsupported_provider"), "우리는 네이버만 지원해요.");
        }

        String provider = oAuth2Userinfo.getGetProvider();
        String providerId = oAuth2Userinfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("password");
        String email = oAuth2Userinfo.getEmail();
        String role = "ROLE_USER";
        String nickName = provider + "_" + getShortUUID(6);
        String loginType = "OAuth";

        Member userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            logger.info("OAuth 로그인이 최초입니다.");
            userEntity = Member.builder()
                    .memId(username)
                    .memPass(password)
                    .memEmail(email)
                    .adminCk(role)
                    .provider(provider)
                    .providerId(providerId)
                    .memNickName(nickName)
                    .loginType(loginType)
                    .build();
            userRepository.saveMember(userEntity);

            session.setAttribute("user",new UserSessionDto(userEntity));
        } else {
            logger.info("로그인을 이미 한적이 있습니다. 당신은 자동 회원가입이 되어 있습니다.");
            session.setAttribute("user",new UserSessionDto(userEntity));
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
    /*
    *  소셜 로그인 닉네임 랜덤 자리 수 생성
    * */
    public static String getShortUUID(int length) {
        if (length <= 0 || length > 36) {
            throw new IllegalArgumentException("길이는 1에서 36 사이로 지정해야 합니다.");
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, length);
    }
}
