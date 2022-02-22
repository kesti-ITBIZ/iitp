package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/verification")
public class VerificationController {
    private final VerificationService verificationService;

    @GetMapping("/getData")
    public ResponseEntity<?> getData(
            @RequestParam("standard") final String standard,
            @RequestParam("compare") final String compare,
            @RequestParam("startDatetime") final String startDatetime,
            @RequestParam("endDatetime") final String endDatetime,
            @RequestParam("stdStnId") final String stdStnId,
            @RequestParam("compStnId") final String compStnId) {
        return ResponseEntity.ok(this.verificationService.getVerificationData(standard, compare, startDatetime, endDatetime, stdStnId, compStnId));
    }
}
