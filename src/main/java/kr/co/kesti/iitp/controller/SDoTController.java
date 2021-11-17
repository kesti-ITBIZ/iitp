package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.SDoTService;
import kr.co.kesti.iitp.vo.SDoTDataParamVO;
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
        return ResponseEntity.ok(this.sDoTService.findAllStations());
    }

    @PostMapping("/getData")
    public ResponseEntity<?> getData(@RequestBody final SDoTDataParamVO request) {
        return ResponseEntity.ok(this.sDoTService.getData(request));
    }
}
