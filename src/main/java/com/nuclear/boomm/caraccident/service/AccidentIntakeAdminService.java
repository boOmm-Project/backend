package com.nuclear.boomm.caraccident.service;

import com.nuclear.boomm.caraccident.domain.AccidentFileEntity;
import com.nuclear.boomm.caraccident.domain.AccidentIntakeEntity;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeResponseDTO;
import com.nuclear.boomm.caraccident.dto.response.AccidentIntakeSpecificDTO;
import com.nuclear.boomm.caraccident.enums.InsuranceClaimStatus;
import com.nuclear.boomm.caraccident.exception.DuplicateAssignException;
import com.nuclear.boomm.caraccident.exception.IntakeNotFoundException;
import com.nuclear.boomm.caraccident.repository.AccidentFileRepository;
import com.nuclear.boomm.caraccident.repository.AccidentIntakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccidentIntakeAdminService {
    private final AccidentFileRepository accidentFileRepository;
    private final AccidentIntakeRepository accidentIntakeRepository;

    /**
     * 제출 완료된 리스트 조회
     */
    public List<AccidentIntakeResponseDTO> getAllSubmitAccidentIntakes() {
        List<AccidentIntakeEntity> entities = accidentIntakeRepository.findAllByIntakeStatusAndIntakeManagerIdIsNull(InsuranceClaimStatus.WRITING_SUBMIT);
        List<AccidentIntakeResponseDTO> dtos = new ArrayList<>();
        for (AccidentIntakeEntity entity : entities) {
            dtos.add(AccidentIntakeResponseDTO.fromEntity(entity));
        }

        return dtos;
    }

    /**
     * 접수 할당 API
     */
    @Transactional
    public void assignIntake(Long userId, Long intakeId){
        AccidentIntakeEntity intake = accidentIntakeRepository.findById(intakeId).orElseThrow(()-> new IntakeNotFoundException("해당 아이디의 접수내역을 찾을 수 없습니다."));
        if(intake.getIntakeManagerId()!=null && !intake.getIntakeManagerId().equals(userId)){
            throw new DuplicateAssignException("이미 다른사람에게 할당된 접수입니다.");
        }
        intake.assignIntakeManager(userId);
        accidentIntakeRepository.save(intake);
    }


    public AccidentIntakeSpecificDTO getSpecificIntake(Long intakeId, Long userId) {
        AccidentIntakeEntity entity = accidentIntakeRepository.findByIdAndIntakeManagerId(intakeId,userId).orElseThrow(()-> new IntakeNotFoundException("해당 아이디의 접수 내역이 존재하지 않거나 접근할 수 없습니다."));
        List<AccidentFileEntity> entities = accidentFileRepository.findAllByAccidentIntakeId(intakeId);

        return AccidentIntakeSpecificDTO.fromEntity(entity,entities);

    }

    @Transactional
    public void changeStatus(Long intakeId, Long userId, InsuranceClaimStatus status) {

        AccidentIntakeEntity entity = accidentIntakeRepository.findByIdAndIntakeManagerId(intakeId,userId).orElseThrow(()-> new IntakeNotFoundException("해당 아이디의 접수 내역이 존재하지 않거나 접근할 수 없습니다."));
        entity.changeStatus(status);
        accidentIntakeRepository.save(entity);
    }

    public List<AccidentIntakeSpecificDTO> getAllMyList(Long userId) {
        List<AccidentIntakeEntity> entities = accidentIntakeRepository.findAllByIntakeManagerId(userId);

        List<AccidentIntakeSpecificDTO> dtos = new ArrayList<>();

        for (AccidentIntakeEntity entity : entities) {
            List<AccidentFileEntity> files = accidentFileRepository.findAllByAccidentIntakeId(entity.getId());
            dtos.add(AccidentIntakeSpecificDTO.fromEntity(entity,files));
        }
        
        return dtos;
    }
}
