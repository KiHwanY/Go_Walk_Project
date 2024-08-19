package com.cos.gowalk.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @Author   : 윤기환
 * @Class    : SecurityConfig.java
 * @Desc     : 스프링 시큐리티 제어 파일
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final AuthenticationFailureHandler customFailureHandler;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> principalOauth2UserService;

    public SecurityConfig(AuthenticationFailureHandler customFailureHandler,
                          OAuth2UserService<OAuth2UserRequest, OAuth2User> principalOauth2UserService) {
        this.customFailureHandler = customFailureHandler;
        this.principalOauth2UserService = principalOauth2UserService;
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.naverClientRegistration());
    }
    private ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId("iZ27MrgWUPNXaHPlkoVL")
                .clientSecret("2blWUpGvWI")
                .scope("name", "email", "nickname")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .clientName("Naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8088/login/oauth2/code/naver")
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated() // 인증된 사용자만 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN 권한 필요
                        .requestMatchers("/login","/loginForm","/joinForm","/basic/idCheck","/join").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**","/trakker_video/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/auth/login") // 로그인 처리 URL
                        .failureHandler(customFailureHandler)
                        .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 URL
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/loginForm")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(principalOauth2UserService) // OAuth2 로그인 사용자 서비스
                        )
                        .failureUrl("/loginForm?error=true")
                        .defaultSuccessUrl("/")

                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청을 처리할 URL 설정
                        .logoutSuccessUrl("/loginForm") // 로그아웃 성공 시 리다이렉트할 URL 설정
                        .addLogoutHandler((request, response, authentication) -> { // 로그아웃 핸들러 추가 (세션 무효화 처리)
                            HttpSession session = request.getSession();
                            session.invalidate();
                        })
                        .logoutSuccessHandler((request, response, authentication) -> // 로그아웃 성공 핸들러 추가 (리다이렉션 처리)
                                response.sendRedirect("/loginForm"))
                        .deleteCookies("remember-me") // 로그아웃 시 쿠키 삭제 설정
                        .invalidateHttpSession(true) // HTTP session reset
                );

        return http.build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

}

