package com.nuclear.boomm.caraccident.domain;

import com.nuclear.boomm.caraccident.dto.request.BrokenObjectDTO;
import com.nuclear.boomm.caraccident.enums.BrokenObejctType;
import com.nuclear.boomm.caraccident.enums.PhoneType;
import com.nuclear.boomm.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BrokenObjectEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "broken_object_type",nullable = false)
    private BrokenObejctType brokenObejctType;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type")
    private PhoneType phoneType;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "repair_place")
    private String repairPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_intake_id")
    private AccidentIntakeEntity accidentIntake;

    @Builder
    public BrokenObjectEntity(BrokenObejctType brokenObejctType, String name, PhoneType phoneType, String phoneNumber, String repairPlace) {
        this.brokenObejctType = brokenObejctType;
        this.name = name;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
        this.repairPlace = repairPlace;
    }

    public void assignAccidentIntake(AccidentIntakeEntity accidentIntake) {
        this.accidentIntake = accidentIntake;
    }

    public static BrokenObjectEntity from(BrokenObjectDTO brokenObjectDTO) {
        return BrokenObjectEntity.builder()
                .brokenObejctType(brokenObjectDTO.brokenObejctType())
                .name(brokenObjectDTO.name())
                .phoneType(brokenObjectDTO.phoneType())
                .repairPlace(brokenObjectDTO.repairPlace())
                .build();
    }
}
