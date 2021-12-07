package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.SDoTService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sDoT")
public class SDoTController {
    private final SDoTService sDoTService;

    @GetMapping("/stations")
    public ResponseEntity<?> stations() {
        return ResponseEntity.ok(this.sDoTService.getSDoTStations());
    }

    @GetMapping("/searchStations")
    public ResponseEntity<?> searchStations(
            @RequestParam("keyword") final String keyword,
            @RequestParam("page") final int page,
            @RequestParam("size") final int size) {
        return ResponseEntity.ok(this.sDoTService.getSDoTStationsByKeyword(keyword, page, size));
    }

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.sDoTService.getSDoTData(request));
    }

    @GetMapping("/getAvailableDatetimes")
    public ResponseEntity<?> getAvailableDatetimes() {
        return ResponseEntity.ok(this.sDoTService.getSDoTAvailableDatetimes());
    }
}
