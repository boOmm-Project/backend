package com.nuclear.boomm.contract.controller;

import com.nuclear.boomm.contract.dto.request.ContractRequest;
import com.nuclear.boomm.contract.dto.response.ContractResponse;
import com.nuclear.boomm.contract.enums.ContractActionType;
import com.nuclear.boomm.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/contracts")
@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<ContractResponse> saveDraftAndSubmit(@RequestBody ContractRequest req) {

        Long draftId = contractService.saveDraftAndSubmit(req);

        if(req.isSubmitAction()) {
            return ResponseEntity.ok(new ContractResponse(
                    draftId,
                    ContractActionType.SUBMITTED.getDescription(),
                    ContractActionType.SUBMITTED
            ));
        } else {
            return ResponseEntity.ok(new ContractResponse(
                    draftId,
                    ContractActionType.SAVED.getDescription(),
                    ContractActionType.SAVED
            ));
        }
    }
}
