package com.nuclear.boomm.caraccident.controller;

import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.service.AccidentIntakeService;
import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/intake")
@Tag(name="intake", description = "사고 접수와 관련된 APi입니다.")
public class AccidentIntakeController {
    private final AccidentIntakeService intakeService;

    @Operation(summary = "자동차 사고 접수 엔티티입니다.")
    @PostMapping()
    public ResponseEntity<ApiResponse<AccidentIntakeIdDTO>> acceptIntake(@Valid @RequestBody AccidentIntakeDTO dto) {
        Long userId = 1L;
        String username = "홍길동";

        return ResponseEntity.status(HttpStatus.CREATED).body(intakeService.acceptIntake(dto, userId, username));

    }
}
