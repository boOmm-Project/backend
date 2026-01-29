package com.nuclear.boomm.auth.oauth.info.impl;

import com.nuclear.boomm.auth.oauth.info.OAuth2UserInfo;
import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        Object response = attributes.get("response");
        if (!(response instanceof Map)) {
            throw new IllegalArgumentException("Naver OAuth2 응답에 response가 없습니다. ");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> casted = (Map<String, Object>) response;        // response 누락시 처리
        this.attributes = casted;
    }

    @Override
    public String getProviderId(){
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider(){
        return "naver";
    }

    @Override
    public String getEmail(){
        return (String) attributes.get("email");
    }

    @Override
    public String getName(){
        return (String) attributes.get("name");
    }

    @Override
    public String getPhone(){
        return (String) attributes.get("mobile");   // naver는 키 값이 "mobile"이다.
    }


}
