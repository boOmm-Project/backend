package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.repository.AccidentIntakeRepository;
import com.nuclear.boomm.common.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccidentIntakeServiceTests {

    @Mock
    AccidentIntakeRepository accidentIntakeRepository;

    @InjectMocks
    AccidentIntakeService accidentIntakeService;

    @Test
    @DisplayName("자동차 사고 접수 성공 테스트 - 모든 값이 정상 일 경우")
    public void acceptAccidentIntake(){

        //given
        Long userId = 1L;
        Long accidentId = 2L;
        LocalDateTime incidientDate = LocalDateTime.now();
        String name = "홍길동";
        String carNumber = "12허12345";
        AccidentIntakeDTO dto = new AccidentIntakeDTO(
                incidientDate,
                carNumber
        );

        AccidentIntakeEntity entity = AccidentIntakeEntity.from(dto, userId,name);
        ReflectionTestUtils.setField(entity,"id", accidentId);

        given(accidentIntakeRepository.existsByInsuredPersonIdAndIncidentDateBetween(userId, incidientDate.minusMinutes(5), incidientDate)).willReturn(false);

        given(accidentIntakeRepository.save(any(AccidentIntakeEntity.class)))
                .willReturn(entity);



        //when

        ApiResponse<AccidentIntakeIdDTO> result =  accidentIntakeService.acceptIntake(dto, userId,name);

        // then

        assertThat(result).isNotNull();
        assertThat(result.getData().intakeId()).isEqualTo(accidentId);
        verify(accidentIntakeRepository).save(any(AccidentIntakeEntity.class));


    }

    @Test
    @DisplayName("자동차 사고 접수 실패 - 5분이내의 값이 있을 경우")
    public void failAcceptAccidentIntakeByDuplicated(){

        //given
        Long userId = 1L;
        LocalDateTime incidientDate = LocalDateTime.now();
        String name = "홍길동";
        String carNumber = "12허12345";
        AccidentIntakeDTO dto = new AccidentIntakeDTO(
                incidientDate,
                carNumber
        );

        given(accidentIntakeRepository.existsByInsuredPersonIdAndIncidentDateBetween(userId, incidientDate.minusMinutes(5), incidientDate)).willReturn(true);



        //when

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accidentIntakeService.acceptIntake(dto, userId,name));


        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("5분 이내의 접수한 건이 존재합니다.");


    }


    @Test
    @DisplayName("자동차 사고 접수 실패 - 미래 날짜 입력")
    public void failAcceptAccidentIntakeByFutureDate(){
        //given
        Long userId = 1L;
        LocalDateTime incidientDate = LocalDateTime.now().plusDays(1);
        String name = "홍길동";
        String carNumber = "12허12345";
        AccidentIntakeDTO dto = new AccidentIntakeDTO(
                incidientDate,
                carNumber
        );

        //when

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accidentIntakeService.acceptIntake(dto, userId, name));

        // then
        assertEquals("사고 일자가 미래일 수는 없습니다.", exception.getMessage());


    }


}