package com.nuclear.boomm.contract.service;

import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.dto.request.ContractRequest;
import com.nuclear.boomm.contract.enums.ProcessingStatus;
import com.nuclear.boomm.contract.repository.DraftContractRepository;
import com.nuclear.boomm.contract.repository.InsurancePolicyRepository;
import com.nuclear.boomm.contract.usecase.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final DraftContractRepository draftRepository;
    private final InsurancePolicyRepository policyRepository;
    private final Validator validator;

    // 신규 등록 및 업데이트
    public Long saveDraftAndSubmit(ContractRequest req) {

        // 이전 작성 내역이 있는지 확인
        DraftContract draft = draftRepository.findTopByUserIdOrderByIdDesc(req.userId())
                .orElseGet(req::toEntity);

        // 입력 값 임시 저장용 테이블에 저장/업데이트
        draft.updateDraftInfo(req);

        // 임시 저장
        DraftContract saved = draftRepository.save(draft);

        // 심사 요청 버튼 클릭시
        if(req.isSubmitAction()) {
            // 유효값 검증 로직 추가(throw 에러)
            validator.validateForSubmit(saved);

            // 심사 요청 완료시 상태 변경
            saved.changeProcessingStatus(ProcessingStatus.UPLOADED);
        }

        return saved.getId();
    }


//    public ContractResponse processReviewResult(ReviewRequest req) {
//        TODO: 신규 계약 메소드 관련 로직 추가
//        return null;
//    }

}
