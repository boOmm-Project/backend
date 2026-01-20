package com.nuclear.boomm.contract.usecase.valid;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.error.CustomException;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidatorTests {

    @InjectMocks
    private ContractValidatorImpl validator;

    private DraftContract.DraftContractBuilder getFullContractBuilder() {
        return DraftContract.builder()
                .userId(1L)
                .productId(10L)
                .insurancePurpose(InsurancePurpose.PROTECTION)
                .medicalHistory("건강함")
                .recentHospitalization(false)
                .startDate(LocalDate.now().plusDays(7))
                .totalPremium(new BigDecimal("50000"));
    }

    @Test
    @DisplayName("상품 미선택시 오류 메시지 출력")
    void should_ThrowException_When_ProductId_Is_Null() {
        assertValidationFailure(
                builder -> builder.productId(null),
                ErrorCode.CONTRACT_PRODUCT_NOT_SELECTED.getMessage()
        );
    }

    @Test
    @DisplayName("가입 목적 미기입시 오류 메시지 출력")
    void should_ThrowException_When_Insurance_Purpose_Is_Null() {
        assertValidationFailure(
                builder -> builder.insurancePurpose(null),
                ErrorCode.CONTRACT_PURPOSE_MISSING.getMessage()
        );
    }

    @Test
    @DisplayName("치료 이력 미기입시 오류 메시지 출력")
    void should_ThrowException_When_Medical_History_Is_Null() {
        assertValidationFailure(
                builder -> builder.medicalHistory(null),
                ErrorCode.CONTRACT_MEDICAL_HISTORY_MISSING.getMessage()
        );
    }

    @Test
    @DisplayName("보험료 계산되지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Premium_Is_Invalid() {
        assertValidationFailure(
                builder -> builder.totalPremium(BigDecimal.ZERO),
                ErrorCode.CONTRACT_PREMIUM_NOT_CALCULATED.getMessage()
        );
    }

    @Test
    @DisplayName("시작일 유효하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_StartDate_Is_In_Past() {
        assertValidationFailure(
                builder -> builder.startDate(LocalDate.now().minusDays(7)),
                ErrorCode.CONTRACT_INVALID_START_DATE.getMessage()
        );
    }

    private void assertValidationFailure(
            Consumer<DraftContract.DraftContractBuilder> mutator,
            String expectedMessage
    ) {
        // [데이터 준비] 기본 우등생 객체 생성
        DraftContract.DraftContractBuilder builder = getFullContractBuilder();

        // 콜백 실행
        mutator.accept(builder);
        DraftContract invalidDraft = builder.build();

        // 실행 및 검증
        CustomException ex = assertThrows(CustomException.class,
                () -> validator.validateForSubmit(invalidDraft));

        // 출력 및 확인
        System.out.println("검증 결과 메시지: " + ex.getErrorCode().getMessage());
        assertThat(ex.getErrorCode().getMessage()).isEqualTo(expectedMessage);
    }
}