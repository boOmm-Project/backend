package com.nuclear.boomm.underwriting.service;

import com.nuclear.boomm.contract.domain.DraftContract;
import com.nuclear.boomm.contract.repository.DraftContractRepository;
import com.nuclear.boomm.underwriting.domain.UnderWriting;
import com.nuclear.boomm.underwriting.dto.request.ChangeUnderWritingStatusRequest;
import com.nuclear.boomm.underwriting.dto.request.DocumentRequest;
import com.nuclear.boomm.underwriting.dto.response.AssignUnderWriterRequest;
import com.nuclear.boomm.underwriting.dto.response.FssSendResponse;
import com.nuclear.boomm.underwriting.dto.response.FssStatusResponse;
import com.nuclear.boomm.underwriting.dto.response.UnderWritingCaseListItemResponse;
import com.nuclear.boomm.underwriting.dto.response.UnderWritingCaseResponse;
import com.nuclear.boomm.underwriting.dto.response.UnderWritingDashboardResponse;
import com.nuclear.boomm.underwriting.enums.RejectReason;
import com.nuclear.boomm.underwriting.enums.UnderWritingStatus;
import com.nuclear.boomm.underwriting.repository.UnderWritingRepository;
import com.nuclear.boomm.vehicle.domain.VehicleInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UnderWritingService {

    private final UnderWritingRepository underWritingRepository;
    private final DraftContractRepository draftContractRepository;

    public UnderWritingDashboardResponse getDashboard() {
        return new UnderWritingDashboardResponse(
                underWritingRepository.countByStatus(UnderWritingStatus.PENDING),
                underWritingRepository.countByStatus(UnderWritingStatus.IN_PROGRESS),
                underWritingRepository.countByStatus(UnderWritingStatus.COMPLETED),
                underWritingRepository.countByStatus(UnderWritingStatus.REJECTED)
        );
    }

    @Transactional(readOnly = true)
    public Page<UnderWritingCaseListItemResponse> getCases(UnderWritingStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "underwritingReviewId"));
        Page<UnderWriting> result =
                (status == null) ? underWritingRepository.findAll(pageable)
                        : underWritingRepository.findAllByStatus(status, pageable);

        return result.map(it -> new UnderWritingCaseListItemResponse(
                it.getUnderwritingReviewId(),
                it.getCustomerId(),
                it.getProductId(),
                it.getStatus(),
                it.getUnderwriterId(),
                it.getReviewedAt()
        ));
    }

    @Transactional(readOnly = true)
    public UnderWritingCaseResponse getCaseDetail(Long id) {
        UnderWriting uw = underWritingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인수심사 케이스가 없습니다. id=" + id));

        return toResponse(uw);
    }

    public UnderWritingCaseResponse assignUnderwriter(Long id, AssignUnderWriterRequest req) {
        UnderWriting uw = underWritingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인수심사 케이스가 없습니다. id=" + id));

        // PENDING에서만 할당 허용 같은 정책도 가능
        uw.assignUnderwriter(req.underwriterId());
        return toResponse(uw);
    }

    public UnderWritingCaseResponse changeStatus(Long id, ChangeUnderWritingStatusRequest req) {
        UnderWriting uw = underWritingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인수심사 케이스가 없습니다. id=" + id));

        // 요구사항 기반: 완료/거절
        if (req.status() == UnderWritingStatus.COMPLETED) {
            // 차량/계약 유효성 체크 (자동차보험 인수심사 핵심)
            validateEligibilityByVehicleAndContract(uw.getCustomerId());
            String msg = (req.message() == null || req.message().isBlank())
                    ? "인수심사가 완료되었습니다."
                    : req.message();
            uw.complete(msg);
            return toResponse(uw);
        }

        if (req.status() == UnderWritingStatus.REJECTED) {
            if (req.rejectReason() == null) {
                throw new IllegalArgumentException("REJECTED 처리에는 rejectReason이 필요합니다.");
            }
            String msg = (req.message() == null || req.message().isBlank())
                    ? "인수심사를 거절하였습니다."
                    : req.message();
            uw.reject(req.rejectReason(), msg);
            return toResponse(uw);
        }

        // PENDING/IN_PROGRESS 등은 여기서 막아도 되고 열어도 됨
        throw new IllegalArgumentException("상태 변경은 COMPLETED/REJECTED만 허용합니다. status=" + req.status());
    }

    @Transactional(readOnly = true)
    public List<RejectReason> getRejectReasons() {
        return List.copyOf(EnumSet.allOf(RejectReason.class));
    }

    public String requestDocuments(Long id, DocumentRequest req) {
        UnderWriting uw = underWritingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인수심사 케이스가 없습니다. id=" + id));

        if (uw.getStatus() != UnderWritingStatus.REJECTED || uw.getRejectReason() != RejectReason.MISSING_DOCUMENT) {
            throw new IllegalStateException("서류보완 요청은 '거절 + 서류누락' 케이스에서만 가능합니다.");
        }

        // 여기서 실제로는 Notification/Message/Email 이벤트 발행
        return (req == null || req.message() == null || req.message().isBlank())
                ? "서류 보완 요청을 전송했습니다."
                : req.message();
    }

    public FssSendResponse sendToFss(Long id) {
        UnderWriting uw = underWritingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인수심사 케이스가 없습니다. id=" + id));

        // 실제: 금감원 전송 API 호출 + 결과 저장
        uw.markFssAdmission(true);
        return new FssSendResponse(id, true, "금감원 전송 요청이 접수되었습니다.");
    }

    @Transactional(readOnly = true)
    public FssStatusResponse getFssStatus(Long id) {
        UnderWriting uw = underWritingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("인수심사 케이스가 없습니다. id=" + id));

        return new FssStatusResponse(id, uw.isFssAdmission(),
                uw.isFssAdmission() ? "금감원 확인 진행/완료 상태입니다." : "미전송 상태입니다.");
    }

    // 자동차보험 인수심사 로직
    private void validateEligibilityByVehicleAndContract(Long customerId) {
        DraftContract draft = draftContractRepository.findTopByUserIdOrderByIdDesc(customerId)
                .orElseThrow(() -> new IllegalStateException("고객의 DraftContract가 없습니다. customerId=" + customerId));

        if (!draft.isCarInsurance()) {
            // 자동차보험이 아닌 계약인데 자동차 인수심사로 들어온 경우
            throw new IllegalStateException("자동차 정보가 없는 계약입니다.");
        }

        VehicleInfo v = draft.getVehicleInfo();
        if (v.getVehicleNumber() == null || v.getVehicleNumber().isBlank()) {
            throw new IllegalStateException("차량번호가 비어있습니다.");
        }

        // 연식 너무 오래된 차량 거절
        if (v.getModelYear() != null && v.getModelYear() < 2000) {
            throw new IllegalStateException("차량 연식이 너무 오래되었습니다.");
        }

        // VIN 없으면 서류누락 취급 가능
        if (v.getVin() == null || v.getVin().isBlank()) {
            throw new IllegalStateException("차대번호(VIN)가 없습니다. 서류 누락 가능성.");
        }
    }

    private UnderWritingCaseResponse toResponse(UnderWriting it) {
        return new UnderWritingCaseResponse(
                it.getUnderwritingReviewId(),
                it.getFileId(),
                it.getCustomerId(),
                it.getProductId(),
                it.getUnderwriterId(),
                it.getContractManagerId(),
                it.isFssAdmission(),
                it.getStatus(),
                it.getRejectReason(),
                it.getResultMessage(),
                it.getReviewedAt()
        );
    }
}