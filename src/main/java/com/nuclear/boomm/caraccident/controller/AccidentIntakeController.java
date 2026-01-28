package com.nuclear.boomm.caraccident.controller;

import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeAccidentDescriptionDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDamageDescriptionDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeFileDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.service.AccidentFileService;
import com.nuclear.boomm.caraccident.service.AccidentIntakeService;
import com.nuclear.boomm.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/intake")
@Tag(name="자동차 사고접수", description = "사고 접수와 관련된 API입니다.")
public class AccidentIntakeController {
    private final AccidentIntakeService intakeService;
    private final AccidentFileService fileService;
    @Operation(summary = "자동차 사고 계약사항 확인 후 작성")
    @PostMapping()
    public ResponseEntity<ApiResponse<AccidentIntakeIdDTO>> acceptIntake(@Valid @RequestBody AccidentIntakeDTO dto) {
        Long userId = 1L;
        String username = "홍길동";

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(intakeService.acceptIntake(dto, userId, username)));

    }

    @Operation(summary = "자동차 사고 접수 사고사항 작성")
    @PatchMapping("/accident/{id}")
    public ResponseEntity<ApiResponse<Void>> updateAccidentDescription(@PathVariable Long id, @Valid @RequestBody AccidentIntakeAccidentDescriptionDTO dto) {
        Long userId = 1L;
        String username = "홍길동";
        intakeService.updateDescription(dto, id, userId, username);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "자동차 사고 접수 피해사항 작성")
    @PatchMapping("/damage/{id}")
    public ResponseEntity<ApiResponse<?>> updateDamageDescription(@PathVariable Long id, @Valid @RequestBody AccidentIntakeDamageDescriptionDTO dto) {
        Long userId = 1L;
        String username = "홍길동";
        if(dto.isChecked()){
            intakeService.updateDamageDescription(dto,id, userId, username);
            return ResponseEntity.ok(ApiResponse.success(null));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("둘 중 하나라도 값이 있어야 합니다."));
        }
    }

    @Operation(summary = "자동차 사고 접수 파일 업로드")
    @PostMapping(value = "/images/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Long>> uploadImage(@PathVariable Long id, @RequestPart MultipartFile file) {
        Long userId = 1L;
        String username = "홍길동";
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(fileService.uploadImage(userId,id,file)));
    }

    @Operation(summary = "자동차 사고 접수 단일 파일 조회")
    @GetMapping("/images/{id}")
    public ResponseEntity<ApiResponse<?>> getImage(@PathVariable Long id, Long fileId){
        Long userId = 1L;
        String username = "홍길동";
        return ResponseEntity.ok(ApiResponse.success(fileService.getIntakeFile(userId, fileId)));
    }

    @Operation(summary = "자동차 사고 접수 전체 파일 조회")
    @GetMapping("/images/{id}/lists")
    public ResponseEntity<ApiResponse<?>> getImageList(@PathVariable Long id){
        Long userId = 1L;
        String username = "홍길동";
        List<AccidentIntakeFileDTO> list = fileService.getIntakeAllFile(userId, id);

        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @Operation(summary = "자동차 사고 접수 이미지 단일 삭제")
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        Long userId = 1L;
        String username = "홍길동";
        fileService.deleteFiles(userId, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "자동차 사고 접수 제출 완료")
    @PatchMapping("/done/{id}")
    public ResponseEntity<ApiResponse<String>> submitIntake(@PathVariable Long id){
        Long userId = 1L;
        String username = "홍길동";
        intakeService.submitIntake(id,userId,username);
        return ResponseEntity.ok(ApiResponse.success("제출완료되었습니다."));
    }
}
