package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.repository.AccidentIntakeRepository;
import com.nuclear.boomm.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccidentIntakeService {

    private final AccidentIntakeRepository accidentIntakeRepository;

    @Transactional
    public ApiResponse<AccidentIntakeIdDTO> acceptIntake(AccidentIntakeDTO dto, Long userId, String username) {
        if (dto.incidentDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("사고 일자가 미래일 수는 없습니다.");
        }
        LocalDateTime interval = dto.incidentDate().minusMinutes(5);
        boolean isDuplicated = accidentIntakeRepository.existsByUserIdAndIncidentDateBetween(userId,interval,dto.incidentDate());

        if(isDuplicated) {
            throw new RuntimeException("5분 이내의 접수한 건이 존재합니다.");
        }

        AccidentIntakeEntity entity = AccidentIntakeEntity.from(dto, userId, username);



        return ApiResponse.success(AccidentIntakeIdDTO.from(accidentIntakeRepository.save(entity).getId()));
    }
}
