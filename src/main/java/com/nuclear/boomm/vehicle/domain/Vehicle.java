package com.nuclear.boomm.vehicle.domain;

import com.nuclear.boomm.vehicle.enums.FuelType;
import com.nuclear.boomm.vehicle.enums.UsePurpose;
import com.nuclear.boomm.vehicle.enums.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "vehicle",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vehicle_number", columnNames = "vehicle_number"),
                @UniqueConstraint(name = "uk_vehicle_vin", columnNames = "vin")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number", nullable = false, length = 20)
    private String vehicleNumber;       // 차량 번호

    @Column(name = "vin", nullable = false, length = 50)
    private String vin;                 // 차대 번호

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
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
