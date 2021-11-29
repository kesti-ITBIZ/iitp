package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.AirkoreaService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/airkorea")
public class AirkoreaController {
    private final AirkoreaService airkoreaService;

    @GetMapping("/stations")
    public ResponseEntity<?> stations() {
        return ResponseEntity.ok(this.airkoreaService.getAirkoreaStations());
    }

    @GetMapping("/searchStations")
    public ResponseEntity<?> stations(@RequestParam("keyword") final String keyword) {
        return ResponseEntity.ok(this.airkoreaService.getAirkoreaStationsByKeyword(keyword));
    }

    @PostMapping("/getDataByDatetime")
    public ResponseEntity<?> getDataByDatetime(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.airkoreaService.getAirkoreaDataByDatetime(request));
    }

    @PostMapping("/getDataByItem")
    public ResponseEntity<?> getDataByItem(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.airkoreaService.getAirkoreaDataByItem(request));
    }
}
