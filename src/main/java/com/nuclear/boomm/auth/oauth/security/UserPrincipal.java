package com.nuclear.boomm.auth.oauth.security;

import com.nuclear.boomm.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private User user;
    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 1개라서 불변 리스트로 주기위해서 singletonList로 받는다.(보안 good)
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoleKey()));
    }


    // OAuth2User 구현(소셜 로그인용) -> 소셜 제공자 변경되도 우리의 기준 ID는 바뀌지않음. good
    @Override
    public String getName() {
        return user.getUserId().toString();
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    // Userdetails 구현(일반 로그인 호환용, 소셜은 pw X )
    @Override
    public String getPassword() {
        return null;    // 소셜 로그인은 PW 없음. 일반 로그인도 같이 쓰려면 분리설계 다시 해야함.
    }
    @Override
    public String getUsername() {
        return user.getEmail();     // 로그인 IDㄹ 이메일 사용.
    }


    // 계정 만료/잠금 여부(일단 true로 설정했음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }



}
