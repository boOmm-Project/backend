package com.nuclear.boomm.contract.usecase.valid;

import com.nuclear.boomm.common.error.ErrorCode;
import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.error.CustomException;
import com.nuclear.boomm.underwriting.enums.InsurancePurpose;
import com.nuclear.boomm.vehicle.domain.VehicleInfo;
import com.nuclear.boomm.vehicle.enums.FuelType;
import com.nuclear.boomm.vehicle.enums.UsePurpose;
import com.nuclear.boomm.vehicle.enums.VehicleType;
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
                .totalPremium(new BigDecimal("50000"))
                .vehicleInfo(VehicleInfo.builder()
                        .vehicleNumber("12가1234")
                        .displacement(3000)
                        .seatCount(9)
                        .vin("12345")
                        .usePurpose(UsePurpose.COMMERCIAL)
                        .manufacturer("아우디")
                        .firstRegistrationDate(LocalDate.now())
                        .fuelType(FuelType.DIESEL)
                        .vehicleType(VehicleType.PASSENGER)
                        .modelName("A8")
                        .modelYear(2025)
                        .build());
    }

    @Test
    @DisplayName("시작일 유효하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Request_Is_Invalid() {
        assertValidationFailure(
                builder -> builder.startDate(LocalDate.now().minusDays(7)),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateForSubmit(draft)
        );
    }

    @Test
    @DisplayName("상품 미선택시 오류 메시지 출력")
    void should_ThrowException_When_ProductId_Is_Null() {
        assertValidationFailure(
                builder -> builder.productId(null),
                ErrorCode.REQUIRED_OPTION_NOT_SELECTED.getMessage(),
                draft -> validator.validateForSubmit(draft)

        );
    }

    @Test
    @DisplayName("가입 목적 미기입시 오류 메시지 출력")
    void should_ThrowException_When_Insurance_Purpose_Is_Null() {
        assertValidationFailure(
                builder -> builder.insurancePurpose(null),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateForSubmit(draft)

        );
    }

    @Test
    @DisplayName("치료 이력 미기입시 오류 메시지 출력")
    void should_ThrowException_When_Medical_History_Is_Null() {
        assertValidationFailure(
                builder -> builder.medicalHistory(null),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateForSubmit(draft)

        );
    }

    @Test
    @DisplayName("보험료 계산되지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Premium_Is_Invalid() {
        assertValidationFailure(
                builder -> builder.totalPremium(BigDecimal.ZERO),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateForSubmit(draft)

        );
    }

    @Test
    @DisplayName("시작일 유효하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_StartDate_Is_In_Past() {
        assertValidationFailure(
                builder -> builder.startDate(LocalDate.now().minusDays(7)),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateForSubmit(draft)

        );
    }

    @Test
    @DisplayName("차량 번호를 기입하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Vehicle_Number_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().vehicleNumber(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("차대 번호를 기입하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Vin_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().vin(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("차종을 선택하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Vehicle_Type_Is_Not_Selected() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().vehicleType(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("제조사를 기입하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Manufacturer_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().manufacturer(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("모델명을 기입하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Model_Name_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().modelName(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("연식을 기입하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Model_Year_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().modelYear(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("연료 타입이 선택되지 않은 경우 ")
    void should_ThrowException_When_Fuel_Type_Is_Not_Selected() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().fuelType(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("사용 목적을 선택하지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Use_Purpose_Is_Invalid() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().usePurpose(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }


    @Test
    @DisplayName("배기량이 기입되지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Displacement_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().displacement(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("승차 정원이 기입되지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_Seat_Count_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().seatCount(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    @Test
    @DisplayName("최초 차량 등록일이 기입되지 않은 경우 오류 메시지 출력")
    void should_ThrowException_When_First_Registration_Date_Is_Null() {
        assertValidationFailure(
                builder -> builder.vehicleInfo(VehicleInfo.builder().firstRegistrationDate(null).build()),
                ErrorCode.REQUIRED_INPUT_MISSING.getMessage(),
                draft -> validator.validateCarInfoForSubmit(draft)
        );
    }

    private void assertValidationFailure(
            Consumer<DraftContract.DraftContractBuilder> mutator,
            String expectedMessage,
            Consumer<DraftContract> validationMethod
    ) {
        // default 데이터 준비
        DraftContract.DraftContractBuilder builder = getFullContractBuilder();

        // 콜백 실행
        mutator.accept(builder);
        DraftContract invalidDraft = builder.build();

        // 실행 및 검증
        CustomException ex = assertThrows(CustomException.class,
                () -> validationMethod.accept(invalidDraft));

        // 출력 및 확인
        System.out.println("검증 결과 메시지: " + ex.getErrorCode().getMessage());
        assertThat(ex.getErrorCode().getMessage()).isEqualTo(expectedMessage);
    }
}