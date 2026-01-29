package com.nuclear.boomm.auth.handler;

import com.nuclear.boomm.auth.oauth.jwt.JwtTokenProvider;
import com.nuclear.boomm.auth.oauth.security.UserPrincipal;
import jakarta.servlet.http.Cookie;
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

        // HttpOnly 쿠키 생성(보안 강화)
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);    // JS 접근 차단(XSS 방지)
        accessTokenCookie.setSecure(false);     // 지금은 HTTPS가 아니라 false로 해놨음 배포 시 true로 바꿔라.
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(60 * 60);   // 쿠키 유효시간(1시간)

        // 응답에 쿠키 추가
        response.addCookie(accessTokenCookie);

        // 토큰에 쿼리 파라미터를 붙여 FE로 Redirect
        String targetUrl = UriComponentsBuilder.fromUriString(FRONTEND_URL + REDIRECT_PATH) // UriComponentsBuilder은 URL을 안전하게 조립해주고, 파라미터를 알아서 처리해주는 도구.
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
