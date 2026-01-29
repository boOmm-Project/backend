package com.nuclear.boomm.caraccident.controller;

import com.nuclear.boomm.caraccident.dto.request.BodilyInjuryRequest;
import com.nuclear.boomm.caraccident.dto.request.PropertyDamageRequest;
import com.nuclear.boomm.caraccident.dto.request.SelfBodilyInjuryRequest;
import com.nuclear.boomm.caraccident.dto.request.SelfVehicleDamageRequest;
import com.nuclear.boomm.caraccident.dto.request.UninsuredMotoristRequest;
import com.nuclear.boomm.caraccident.service.AccidentAssessmentService;
import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/assessments")
@RequiredArgsConstructor
@Tag(name = "자동차 사고보상 API", description = "사고 유형별 심사 청구 등록 API")
public class AccidentAssessmentController {
    private final AccidentAssessmentService assessmentService;

    @Operation(summary = "대물 배상 심사 청구", description = "차량 파손 등 대물 피해에 대한 심사를 접수합니다.")
    @PostMapping("/property-damage")
    public ResponseEntity<ApiResponse<?>> createPropertyDamage(
            @RequestBody @Valid PropertyDamageRequest dto) {

        assessmentService.createProperty(dto);
        return ResponseEntity.ok(ApiResponse.success("대물 배상 심사가 성공적으로 접수되었습니다."));
    }

    @Operation(summary = "대인 배상 심사 청구", description = "사람의 부상 등 대인 피해에 대한 심사를 접수합니다.")
    @PostMapping("/bodily-injury")
    public ResponseEntity<ApiResponse<?>> createBodilyInjury(
            @RequestBody @Valid BodilyInjuryRequest dto) {

        assessmentService.createBodilyInjury(dto);
        return ResponseEntity.ok(ApiResponse.success("대인 배상 심사가 성공적으로 접수되었습니다."));
    }

    @Operation(summary = "자기신체사고 심사 청구", description = "운전자 본인의 신체 피해(자손)에 대한 심사를 접수합니다.")
    @PostMapping("/self-bodily-injury")
    public ResponseEntity<ApiResponse<?>> createSelfBodilyInjury(
            @RequestBody @Valid SelfBodilyInjuryRequest dto) {

        assessmentService.createSelfBodilyInjury(dto);
        return ResponseEntity.ok(ApiResponse.success("자기신체사고 심사가 성공적으로 접수되었습니다."));
    }

    @Operation(summary = "자기차량손해(자차) 심사 청구", description = "본인 차량 파손(자차)에 대한 심사를 접수합니다.")
    @PostMapping("/self-vehicle-damage")
    public ResponseEntity<ApiResponse<?>> createSelfVehicleDamage(
            @RequestBody @Valid SelfVehicleDamageRequest dto) {

        assessmentService.createSelfVehicleDamage(dto);
        return ResponseEntity.ok(ApiResponse.success("자기차량손해 심사가 성공적으로 접수되었습니다."));
    }

    @Operation(summary = "무보험차상해 심사 청구", description = "상대방이 무보험인 경우에 대한 심사를 접수합니다.")
    @PostMapping("/uninsured-motorist")
    public ResponseEntity<ApiResponse<?>> createUninsuredMotorist(
            @RequestBody @Valid UninsuredMotoristRequest dto) {

        assessmentService.createUninsuredMotorist(dto);
        return ResponseEntity.ok(ApiResponse.success("무보험차상해 심사가 성공적으로 접수되었습니다."));
    }



}
