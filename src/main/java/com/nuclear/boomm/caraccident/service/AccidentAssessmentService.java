package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.domain.BodilyInjuryEntity;
import com.nuclear.boomm.caraccident.domain.PropertyDamage;
import com.nuclear.boomm.caraccident.domain.SelfBodilyInjury;
import com.nuclear.boomm.caraccident.domain.SelfVehicleDamage;
import com.nuclear.boomm.caraccident.domain.UninsuredMotorist;
import com.nuclear.boomm.caraccident.dto.request.BodilyInjuryRequest;
import com.nuclear.boomm.caraccident.dto.request.PropertyDamageRequest;
import com.nuclear.boomm.caraccident.dto.request.SelfBodilyInjuryRequest;
import com.nuclear.boomm.caraccident.dto.request.SelfVehicleDamageRequest;
import com.nuclear.boomm.caraccident.dto.request.UninsuredMotoristRequest;
import com.nuclear.boomm.caraccident.exception.IntakeNotFoundException;
import com.nuclear.boomm.caraccident.repository.AccidentIntakeRepository;
import com.nuclear.boomm.caraccident.repository.GeneralAccidentAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccidentAssessmentService {
    private final GeneralAccidentAssessmentRepository assessmentRepository;
    private final AccidentIntakeRepository accidentIntakeRepository;

    @Transactional
    public void createProperty(PropertyDamageRequest dto) {
        validateIntakeId(dto.getAccidentIntakeId());


        PropertyDamage entity = dto.toEntity();


        assessmentRepository.save(entity);
    }

    @Transactional
    public void createBodilyInjury(BodilyInjuryRequest dto) {
        validateIntakeId(dto.getAccidentIntakeId());

        BodilyInjuryEntity entity = dto.toEntity();

        assessmentRepository.save(entity);
    }

    @Transactional
    public void createSelfBodilyInjury(SelfBodilyInjuryRequest dto) {
        validateIntakeId(dto.getAccidentIntakeId());

        SelfBodilyInjury entity = dto.toEntity();

        assessmentRepository.save(entity);
    }

    @Transactional
    public void createSelfVehicleDamage(SelfVehicleDamageRequest dto) {
        validateIntakeId(dto.getAccidentIntakeId());

        SelfVehicleDamage entity = dto.toEntity();

        assessmentRepository.save(entity);
    }

    @Transactional
    public void createUninsuredMotorist(UninsuredMotoristRequest dto) {
        validateIntakeId(dto.getAccidentIntakeId());

        UninsuredMotorist entity = dto.toEntity();

        assessmentRepository.save(entity);
    }

    private void validateIntakeId(Long intakeId) {
        boolean exists = accidentIntakeRepository.existsById(intakeId);
        if (!exists) {
            throw new IntakeNotFoundException("존재하지 않는 사고 접수 번호입니다: " + intakeId);
        }
    }
}
