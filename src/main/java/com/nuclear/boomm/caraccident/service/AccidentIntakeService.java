package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import com.nuclear.boomm.caraccident.domain.BrokenObjectEntity;
import com.nuclear.boomm.caraccident.domain.InjuryPersonEntity;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeAccidentDescriptionDTO;
import com.nuclear.boomm.caraccident.dto.request.AccidentIntakeDamageDescriptionDTO;
import com.nuclear.boomm.caraccident.dto.request.BrokenObjectDTO;
import com.nuclear.boomm.caraccident.dto.request.InjuryPersonDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeIdDTO;
import com.nuclear.boomm.caraccident.exception.IntakeNotFoundException;
import com.nuclear.boomm.caraccident.repository.AccidentIntakeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccidentIntakeService {

    private final AccidentIntakeRepository accidentIntakeRepository;

    @Transactional
    public AccidentIntakeIdDTO acceptIntake(AccidentIntakeDTO dto, Long userId, String username) {
        if (dto.incidentDate().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("사고 일자가 미래일 수는 없습니다.");
        }
        LocalDateTime interval = dto.incidentDate().minusMinutes(5);
        boolean isDuplicated = accidentIntakeRepository.existsByInsuredPersonIdAndIncidentDateBetween(userId,interval,dto.incidentDate());

        if(isDuplicated) {
            throw new RuntimeException("5분 이내의 접수한 건이 존재합니다.");
        }

        AccidentIntakeEntity entity = AccidentIntakeEntity.from(dto, userId, username);



        return AccidentIntakeIdDTO.from(accidentIntakeRepository.save(entity).getId());
    }

    // TODO: 내용 입력
    @Transactional
    public void updateDescription(AccidentIntakeAccidentDescriptionDTO dto, Long intakeId, Long userId, String username) {
        AccidentIntakeEntity entity = accidentIntakeRepository.findByIdAndInsuredPersonIdAndInsuranceClaimPersonName(intakeId,userId,username).orElseThrow(
                ()-> new IntakeNotFoundException("사고 접수건을 찾을 수 없습니다."));

        entity.updateDescription(dto);

        accidentIntakeRepository.save(entity);
    }

    @Transactional
    public void updateDamageDescription(@Valid AccidentIntakeDamageDescriptionDTO dto, Long intakeId, Long userId, String username) {
        AccidentIntakeEntity entity = accidentIntakeRepository.findByIdAndInsuredPersonIdAndInsuranceClaimPersonName(intakeId,userId,username).orElseThrow(
                ()-> new IntakeNotFoundException("사고 접수건을 찾을 수 없습니다."));

        entity.getBrokenObjects().clear();

        if (dto.brokenList() != null) {


            for (BrokenObjectDTO object : dto.brokenList()) {
                entity.addBrokenObject(BrokenObjectEntity.from(object));
            }
        }
        entity.getInjuryPersons().clear();
        if (dto.personList() != null) {
            for (InjuryPersonDTO object : dto.personList()) {
                entity.addInjuryPerson(InjuryPersonEntity.from(object));
            }
        }
    }
}
