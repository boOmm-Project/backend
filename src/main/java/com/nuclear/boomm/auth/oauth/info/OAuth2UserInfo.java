package com.nuclear.boomm.auth.oauth.info;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
    String getPhone();
}
