package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.KTService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kt")
public class KTController {
    private final KTService ktService;

    @GetMapping("/stations")
    public ResponseEntity<?> stations(
            @RequestParam("startDatetime") final String startDatetime,
            @RequestParam("endDatetime") final String endDatetime) throws ParseException {
        return ResponseEntity.ok(this.ktService.getKtStations(startDatetime, endDatetime));
    }

    @GetMapping("/searchStations")
    public ResponseEntity<?> searchStations(
            @RequestParam("keyword") final String keyword,
            @RequestParam("page") final int page,
            @RequestParam("size") final int size) {
        return ResponseEntity.ok(this.ktService.getKtStationsByKeyword(keyword, page, size));
    }

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.ktService.getKtData(request));
    }

    @GetMapping("/getAvailableDatetimes")
    public ResponseEntity<?> getAvailableDatetimes() {
        return ResponseEntity.ok(this.ktService.getKtAvailableDatetimes());
    }
}
