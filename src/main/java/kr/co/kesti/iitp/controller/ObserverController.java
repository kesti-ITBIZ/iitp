package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.ObserverService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/observer")
public class ObserverController {
    private final ObserverService observerService;

    @GetMapping("/searchStations")
    public ResponseEntity<?> searchStations(
            @RequestParam("keyword") final String keyword,
            @RequestParam("page") final int page,
            @RequestParam("size") final int size) {
        return ResponseEntity.ok(this.observerService.getObserverStationsByKeyword(keyword, page, size));
    }

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.observerService.getObserverData(request));
    }

    @GetMapping("/getCompareData")
    public ResponseEntity<?> getCompareData(
            @RequestParam("startDatetime") final String startDatetime,
            @RequestParam("endDatetime") final String endDatetime,
            @RequestParam("stdStnNm") final String stdStnNm,
            @RequestParam("compStnId") final String compStnId) {
        return ResponseEntity.ok(this.observerService.getCompareWithObserverData(startDatetime, endDatetime, stdStnNm, compStnId));
    }

    @GetMapping("/getAvailableDatetimes")
    public ResponseEntity<?> getAvailableDatetimes() {
        return ResponseEntity.ok(this.observerService.getObserverAvailableDatetimes());
    }
}
