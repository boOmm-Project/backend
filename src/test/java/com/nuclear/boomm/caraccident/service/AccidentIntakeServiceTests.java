package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDescriptionDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.enums.InsuranceClaimStatus;
import com.nuclear.boomm.caraccident.exception.IntakeNotFoundException;
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
import java.util.Optional;

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




}