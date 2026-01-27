package com.nuclear.boomm.vehicle.domain;

import com.nuclear.boomm.vehicle.enums.FuelType;
import com.nuclear.boomm.vehicle.enums.UsePurpose;
import com.nuclear.boomm.vehicle.enums.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class VehicleInfo {

    @Column(name = "vehicle_number", length = 20)
    private String vehicleNumber;       // 차량 번호

    @Column(name = "vin", length = 50)
    private String vin;                 // 차대 번호

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;    // 차종

    @Column(name = "manufacturer", length = 50)
    private String manufacturer;        // 제조사

    @Column(name = "model_name", length = 50)
    private String modelName;           // 모델명

    @Column(name = "model_year")
    private Integer modelYear;          // 연식

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_purpose")
    private UsePurpose usePurpose;

    @Column(name = "displacement")
    private Integer displacement;       // 배기량

    @Column(name = "seat_count")
    private Integer seatCount;          // 승차 정원

    @Column(name = "first_registration_date")
    private LocalDate firstRegistrationDate;    // 최초 차량 등록일(출고 날짜)
}
