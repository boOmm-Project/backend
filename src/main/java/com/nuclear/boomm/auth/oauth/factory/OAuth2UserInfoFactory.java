package com.nuclear.boomm.auth.oauth.factory;

import com.nuclear.boomm.auth.oauth.info.OAuth2UserInfo;
import com.nuclear.boomm.auth.oauth.info.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if ("naver".equalsIgnoreCase(registrationId)){
            return new NaverOAuth2UserInfo(attributes);
        }
        throw new IllegalArgumentException("서버에서 지원하지 않는 소셜 로그인입니다.");
    }
}
