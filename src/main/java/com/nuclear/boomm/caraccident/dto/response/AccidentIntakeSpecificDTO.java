package com.nuclear.boomm.caraccident.dto.response;

import com.nuclear.boomm.caraccident.domain.AccidentFileEntity;
import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;

import java.time.LocalDateTime;
import java.util.List;

public record AccidentIntakeSpecificDTO(
        Long intakeId,
        String Inclaimstatus,
        Long insuredPersonId,
        String insuranceClaimPersonName,
        LocalDateTime incidentDate,
        String accidentDescription,
        String zipCode,
        String accidentPlace,
        String detailPlace,
        String accidentType,
        Boolean isReport,
        String policeStation,
        List<BrokenObjectResponseDTO> brokenObjects,
        List<InjuryPersonResponseDTO> injuryPersons,
        List<AccidentIntakeFileDTO> files
) {
    public static AccidentIntakeSpecificDTO fromEntity(AccidentIntakeEntity entity, List<AccidentFileEntity> entities) {

        List<BrokenObjectResponseDTO> brokenList = entity.getBrokenObjects().stream()
                .map(BrokenObjectResponseDTO::fromEntity)
                .toList();

        List<InjuryPersonResponseDTO> injuryList = entity.getInjuryPersons().stream()
                .map(InjuryPersonResponseDTO::fromEntity)
                .toList();


        List<AccidentIntakeFileDTO> list = entities.stream()
                .map(AccidentIntakeFileDTO::fromEntity)
                .toList();

        return new AccidentIntakeSpecificDTO(
                entity.getId(),
                entity.getIntakeStatus().getDescription(),
                entity.getInsuredPersonId(),
                entity.getInsuranceClaimPersonName(),
                entity.getIncidentDate(),
                entity.getAccidentDescription(),
                entity.getZipCode(),
                entity.getAccidentPlace(),
                entity.getDetailPlace(),
                entity.getAccidentType().getDescription(),
                entity.getIsReport(),
                entity.getPoliceStation(),
                brokenList,
                injuryList,
                list);
    }
}
