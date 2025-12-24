package com.nuclear.boomm.auth.oauth.service;

import com.nuclear.boomm.auth.oauth.factory.OAuth2UserInfoFactory;
import com.nuclear.boomm.auth.oauth.info.OAuth2UserInfo;
import com.nuclear.boomm.auth.oauth.security.UserPrincipal;
import com.nuclear.boomm.user.domain.User;
import com.nuclear.boomm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException{

        // 네이버에서 유저 정보 가져오기.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("Social Login Info : {}" , oAuth2User.getAttributes());

        // 어떤 소셜이지 확인.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 팩토리로 정보 가공(OCP)
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        // user 저장 또는 업데이트
        User user = saveOrUpdate(userInfo);

        // 시큐리티 세션에 저장한 userPrincipal 반환.
        return new UserPrincipal(user, oAuth2User.getAttributes());

    }

    private User saveOrUpdate(OAuth2UserInfo userInfo) {

        User user = userRepository.findByEmail(userInfo.getEmail())
                .map(entity -> entity.update(userInfo.getName(), userInfo.getEmail()))
                .orElse(User.builder()
                        .name(userInfo.getName())
                        .email(userInfo.getEmail())
                        .phone(userInfo.getPhone())
                        .provider(userInfo.getProvider())
                        .providerId(userInfo.getProviderId())
                        .build());

        return userRepository.save(user);

    }

}
