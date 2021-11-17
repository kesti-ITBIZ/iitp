package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.AirkoreaService;
import kr.co.kesti.iitp.vo.AirkoreaDataParamVO;
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
        return ResponseEntity.ok(this.airkoreaService.findAllStations());
    }

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody final AirkoreaDataParamVO request) {
        return ResponseEntity.ok(this.airkoreaService.getData(request));
    }
}
