package com.nuclear.boomm.auth.oauth.info.impl;

import com.nuclear.boomm.auth.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
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
        return (String) attributes.get("moblie");   // naver는 키 값이 "mobile"이다.
    }


}
