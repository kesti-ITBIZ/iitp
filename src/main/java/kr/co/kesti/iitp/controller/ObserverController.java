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

    @GetMapping("/getAvailableDatetimes")
    public ResponseEntity<?> getAvailableDatetimes() {
        return ResponseEntity.ok(this.observerService.getObserverAvailableDatetimes());
    }
}
