package com.nuclear.boomm.caraccident.domain;

import com.nuclear.boomm.caraccident.enums.InjuryPersonType;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InjuryPersonEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "injury_person_type")
    private InjuryPersonType injuryPersonType;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type")
    private PhoneType phoneType;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hospital_name")
    private String hospitalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_intake_id")
    private AccidentIntakeEntity accidentIntake;

    @Builder
    public InjuryPersonEntity(InjuryPersonType injuryPersonType, String name, PhoneType phoneType, String phoneNumber, String hospitalName) {
        this.injuryPersonType = injuryPersonType;
        this.name = name;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
        this.hospitalName = hospitalName;
    }

    public void assignAccidentIntake(AccidentIntakeEntity accidentIntake) {
        this.accidentIntake = accidentIntake;
    }
}
