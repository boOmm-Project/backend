package com.nuclear.boomm.auth.handler;

import com.nuclear.boomm.auth.oauth.jwt.JwtTokenProvider;
import com.nuclear.boomm.auth.oauth.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private  final JwtTokenProvider jwtTokenProvider;
    // FE 주소(담에 yml로 빼자) -> 배포하면 주소가 바뀌기 때문에 앞을 변수로 따로 뺀 것이다.
    private static final String FRONTEND_URL = "http://localhost:3000";
    // Redirect 될 경로(Vue 라우터에서 경로 만들기)
    private static final String REDIRECT_PATH = "/login/callback";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("OAuth2 로그인 성공! 토큰 생성할게!!! ");

        // 로그인된 정보 가져오기
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(
                userPrincipal.getUser().getEmail(),
                userPrincipal.getUser().getRoleKey()
        );

        log.info("생성된 토큰() : ", accessToken);

        // 토큰에 쿼리 파라미터를 붙여 FE로 Redirect
        String targetUrl = UriComponentsBuilder.fromUriString(FRONTEND_URL + REDIRECT_PATH) // UriComponentsBuilder은 URL을 안전하게 조립해주고, 파라미터를 알아서 처리해주는 도구.
                .queryParam("token", accessToken)                                    // 그냥 문자열 더하기(+)로 URL을 만들면 위험함.
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
