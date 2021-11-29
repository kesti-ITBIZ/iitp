package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.ObserverService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/observer")
public class ObserverController {
    private final ObserverService observerService;

    @GetMapping("/stations")
    public ResponseEntity<?> stations() {
        return ResponseEntity.ok(this.observerService.getObserverStations());
    }

    @GetMapping("/searchStations")
    public ResponseEntity<?> stations(@RequestParam("keyword") final String keyword) {
        return ResponseEntity.ok(this.observerService.getObserverStationsByKeyword(keyword));
    }

    @PostMapping("/getDataByDatetime")
    public ResponseEntity<?> getDataByDatetime(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.observerService.getObserverDataByDatetime(request));
    }

    @PostMapping("/getDataByItem")
    public ResponseEntity<?> getDataByItem(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.observerService.getObserverDataByItem(request));
    }
}
