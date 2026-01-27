package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.repository.AccidentIntakeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccidentIntakeServiceTests {

    @Mock
    AccidentIntakeRepository accidentIntakeRepository;

    @InjectMocks
    AccidentIntakeService accidentIntakeService;




}