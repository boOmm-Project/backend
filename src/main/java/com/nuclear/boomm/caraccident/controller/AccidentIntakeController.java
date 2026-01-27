package com.nuclear.boomm.caraccident.controller;

import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDamageDescriptionDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.service.AccidentIntakeService;
import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/intake")
@Tag(name="자동차 사고접수", description = "사고 접수와 관련된 API입니다.")
public class AccidentIntakeController {
    private final AccidentIntakeService intakeService;

    @Operation(summary = "자동차 사고 계약사항 확인 후 작성")
    @PostMapping()
    public ResponseEntity<ApiResponse<AccidentIntakeIdDTO>> acceptIntake(@Valid @RequestBody AccidentIntakeDTO dto) {
        Long userId = 1L;
        String username = "홍길동";

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(intakeService.acceptIntake(dto, userId, username)));

    }

    @Operation(summary = "자동차 사고 접수 사고사항 작성")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateDescription(@PathVariable Long id, @Valid @RequestBody AccidentIntakeDamageDescriptionDTO dto) {
        Long userId = 1L;
        String username = "홍길동";
        intakeService.updateDescription(dto, id, userId, username);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
