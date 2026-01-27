package com.nuclear.boomm.user.repository;


import com.nuclear.boomm.user.domain.User;
import com.nuclear.boomm.user.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @Test
    @DisplayName("회원 저장 및 조회 테스트 : 성공 ! ")
    void saveAndFindUser_Success(){
        // given
        testUser  = User.builder()
                .email("test@naver.com")
                .name("test11")
                .role(Role.USER)
                .provider("naver")
                .providerId("testId_1")
                .build();

        given(userRepository.save(any(User.class))).willReturn(testUser);
        given(userRepository.findByEmail("test@naver.com")).willReturn(Optional.of(testUser));

        // when
        User savedUser = userRepository.save(testUser);
        User foundUser = userRepository.findByEmail("test@naver.com").orElse(null);


        // then
        assertThat(savedUser).isNotNull();
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@naver.com");
        assertThat(foundUser.getName()).isEqualTo("test11");
        assertThat(foundUser.getRole()).isEqualTo(Role.USER);

        verify(userRepository, times(1)).save(testUser);
        verify(userRepository, times(1)).findByEmail("test@naver.com");
    }

    @Test
    @DisplayName("회원 저장 및 조회 테스트 : 유저 없음 ! ")
    void saveAndFindUser_UserNotFound(){
        // given
        testUser = User.builder()
                .email("test@naver.com")
                .name("test11")
                .role(Role.USER)
                .provider("naver")
                .providerId("testId_1")
                .build();

        given(userRepository.save(any(User.class))).willReturn(testUser);
        given(userRepository.findByEmail("test@naver.com")).willReturn(Optional.empty());       // 유저 없음


        // when
        User savedUser = userRepository.save(testUser);
        User foundUser = userRepository.findByEmail("test@naver.com").orElse(null);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(foundUser).isNull();     // 유저 찾지 못함.

        verify(userRepository, times(1)).save(testUser);
        verify(userRepository, times(1)).findByEmail("test@naver.com");

    }
}
