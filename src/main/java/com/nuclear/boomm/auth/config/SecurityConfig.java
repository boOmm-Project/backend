package com.nuclear.boomm.auth.config;

import com.nuclear.boomm.auth.handler.OAuth2SuccessHandler;
import com.nuclear.boomm.auth.oauth.service.CustomOAuth2UserService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ServletRequest httpServletRequest) throws Exception {
        http
                // CSRF 비활성화(JWT 방식이므로 불필요함)
                .csrf(AbstractHttpConfigurer::disable)

                // 세션 사용 안함(JWT가 상태 관리 대신함 )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // swagger 경로
                        .requestMatchers(
                                "/swagger-ui/**"
//                                , "/swagger-ui.html"
//                                , "/swagger-ui/index.html"
                                , "/v3/api-docs/**"
                                , "/swagger-resources/**"
//                                , "/webjars/**"
                        ).permitAll()

                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated() // 나머지는 로그인 필요
                )

                // OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler)

                );
        return http.build();

    }

}
