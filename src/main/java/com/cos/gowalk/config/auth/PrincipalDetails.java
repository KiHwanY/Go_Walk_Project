package com.cos.gowalk.config.auth;


import com.cos.gowalk.model.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @Author   : 윤기환
 * @Class    : PrincipalDetailsService.java
 * @Desc     : 시큐리티 유저 정보 담는 인터페이스
 * */
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {


    private Member member; // 콤포지션
    private Map<String, Object> attributes;

    // 일반 로그인 생성자
    public PrincipalDetails(Member member) {
        this.member=member;
    }
    //OAuth 로그인 생성자
    public PrincipalDetails(Member member,
                            Map<String, Object> attributes) {
        this.member=member;
        this.attributes=attributes;
    }



    //해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>(); // ArrayList는 Collection의 자식 타입이다.
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getAdminCk(); // Object 타입을 String 으로 변환해서 return
            }
        });
        return collect;
    }
    @Override // password
    public String getPassword() {
        return member.getMemPass();
    }

    @Override // name
    public String getUsername() {
        return member.getMemId();
    }

    // 계정 만료 여부
    @Override // 해당 계정 만료 했는지?
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 잠김 여부
    @Override // 해당 계정이 잠겨 있는지?
    public boolean isAccountNonLocked() {
        return true;
    }
    // 계정만료 기간이 지났는지 여부
    @Override // 해당 계정의 비밀번호가 1년이 지났는지?
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 활성화 여부

    @Override // 해당 계정이 활성화 되어있는지?
    public boolean isEnabled() {

        // 우리 사이트!! 1년동안 회원이 로그인을 안하면 !! 휴먼 계정으로 하기로 함.
        // 현재시간 - 로긴시간 => 1년을 초과하면 return false;
        return true;
    }
    // OAuth2User 등록하면 getAttributes,getName 메서드가 오버라이드 된다.
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override // 사용안함
    public String getName() {
        return null;

    }
}
