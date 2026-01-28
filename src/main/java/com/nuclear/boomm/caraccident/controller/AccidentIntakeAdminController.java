package com.nuclear.boomm.caraccident.controller;

import com.nuclear.boomm.caraccident.service.AccidentIntakeAdminService;
import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/intake")
@Tag(name="접수담당자",description = "접수담당자 전용 API입니다.")
public class AccidentIntakeAdminController {

    private final AccidentIntakeAdminService intakeAdminService;

    @Operation(summary = "제출완료된 접수 목록을 가져옵니다.")
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAllLists(){
        Long userId = 2L;

        return ResponseEntity.ok(ApiResponse.success(intakeAdminService.getAllSubmitAccidentIntakes()));
    }

    @Operation(summary = "현재 접속되어있는 담당자가 해당 접수를 할당받습니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> assignIntake(@PathVariable Long id){
        Long userId = 2L;
        intakeAdminService.assignIntake(userId, id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
