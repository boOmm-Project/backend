package com.nuclear.boomm.contract.service;

import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.dto.request.ContractRequest;
import com.nuclear.boomm.contract.enums.ProcessingStatus;
import com.nuclear.boomm.contract.repository.DraftContractRepository;
import com.nuclear.boomm.contract.usecase.valid.Validator;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class ContractServiceTests {

    @Mock
    private DraftContractRepository draftRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private ContractService contractService;

    @Test
    @DisplayName("이전 임시 저장 내역이 없는 경우 새 엔티티 생성")
    void should_Create_New_Draft_When_No_Existing_Record() {
        // given
        Long userId = 1L;
        ContractRequest req = new ContractRequest(
                userId,
                10L, // productId
                InsurancePurpose.PROTECTION, // 보험 목적
                "과거 수술 이력 없음", // 질병 이력
                false, // 최근 입원 여부
                LocalDate.now().plusDays(7), // 시작일
                new BigDecimal("50000"), // 보험료
                false);

        // 이전 임시 저장 내역이 비어있도록 반환
        when(draftRepository.findTopByUserIdOrderByIdDesc(anyLong())).thenReturn(Optional.empty());

        DraftContract newDraft = req.toEntity();
        DraftContract savedDraft = spy(newDraft);
        when(savedDraft.getId()).thenReturn(1L);

        when(draftRepository.save(any(DraftContract.class))).thenReturn(savedDraft);

        // when
        Long savedId = contractService.saveDraftAndSubmit(req);

        // then
        assertThat(savedId).isEqualTo(1L);
        verify(draftRepository).save(any(DraftContract.class));
        System.out.println("생성된 임시 생성 아이디 : " + savedId);
    }

    @Test
    @DisplayName("이전 작성 내역이 있는 경우 기존 엔티티를 가져와서 정보를 업데이트")
    void should_Retrieve_Existing_Draft_When_Record_Exists() {
        // given
        Long userId = 1L;

        DraftContract existingDraft = DraftContract.builder()
                .id(100L)
                .userId(userId)
                .medicalHistory("과거 수술 이력 없음")
                .build();

        ContractRequest req = new ContractRequest(
                userId, 10L, InsurancePurpose.PROTECTION,
                "비염 있음", false, LocalDate.now(),
                new BigDecimal("30000"), false
        );

        when(draftRepository.findTopByUserIdOrderByIdDesc(userId)).thenReturn(Optional.of(existingDraft));

        when(draftRepository.save(any(DraftContract.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Long savedId = contractService.saveDraftAndSubmit(req);

        // then
        assertThat(savedId).isEqualTo(100L);
        System.out.println("임시 저장 아이디 : " + savedId);

        assertThat(existingDraft.getMedicalHistory()).isEqualTo("비염 있음");
        System.out.println("변경된 치료 이력 : " + existingDraft.getMedicalHistory());

        verify(draftRepository).findTopByUserIdOrderByIdDesc(userId);
        verify(draftRepository).save(existingDraft);
        System.out.println("계약 가입자 아이디 : " + userId);
    }

    @Test
    @DisplayName("입력받은 정보들이 엔티티의 각 필드에 올바르게 업데이트되었는지 확인")
    void should_Update_Draft_Details_With_Request_Data() {
        // given
        Long userId = 1L;
        BigDecimal premium = new BigDecimal("50000");
        LocalDate startDate = LocalDate.of(2026, 2, 1);

        ContractRequest req = new ContractRequest(
                userId,
                10L,
                InsurancePurpose.PROTECTION,
                "비염 및 약복용 중",
                true,
                startDate,
                premium,
                false
        );

        DraftContract draft = DraftContract.builder().userId(userId).build();
        when(draftRepository.findTopByUserIdOrderByIdDesc(userId)).thenReturn(Optional.of(draft));
        when(draftRepository.save(any(DraftContract.class))).thenAnswer(i -> i.getArgument(0));

        // when
        contractService.saveDraftAndSubmit(req);

        // then
        assertThat(draft.getMedicalHistory()).isEqualTo("비염 및 약복용 중");
        System.out.println("치료 이력 : " + draft.getMedicalHistory());
        assertThat(draft.isRecentHospitalization()).isTrue();
        System.out.println("최근 5년 내 입원/수술 여부 : " +  draft.isRecentHospitalization());
        assertThat(draft.getTotalPremium()).isEqualByComparingTo(premium);
        System.out.println("산출된 보험료 : " + draft.getTotalPremium());
        assertThat(draft.getStartDate()).isEqualTo(startDate);
        System.out.println("보험 시작일 : " + draft.getStartDate());

        assertThat(draft.getProcessingStatus()).isEqualTo(null);
    }


    @Test
    @DisplayName("심사 요청 버튼을 누르면 상태가 UPLOADED로 변경되어야 한다")
    void should_Change_Status_To_Uploaded_When_Submitted() {
        // given
        Long userId = 1L;
        ContractRequest req = new ContractRequest(
                userId, 10L, InsurancePurpose.PROTECTION,
                "특이사항 없음", false, LocalDate.now(),
                new BigDecimal("30000"),
                true
        );

        DraftContract draft = DraftContract.builder()
                .userId(userId)
                .processingStatus(null)
                .build();

        when(draftRepository.findTopByUserIdOrderByIdDesc(userId)).thenReturn(Optional.of(draft));
        when(draftRepository.save(any(DraftContract.class))).thenAnswer(i -> i.getArgument(0));

        // when
        contractService.saveDraftAndSubmit(req);

        // then
        assertThat(draft.getProcessingStatus()).isEqualTo(ProcessingStatus.UPLOADED);
        System.out.println("현재 심사 상태 : " +  draft.getProcessingStatus());
        verify(validator).validateForSubmit(draft);
    }
}