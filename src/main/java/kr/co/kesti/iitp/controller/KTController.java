package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.KTService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kt")
public class KTController {
    private final KTService ktService;

    @GetMapping("/stations")
    public ResponseEntity<?> stations() {
        return ResponseEntity.ok(this.ktService.getKtStations());
    }

    @GetMapping("/searchStations")
    public ResponseEntity<?> stations(@RequestParam("keyword") final String keyword) {
        return ResponseEntity.ok(this.ktService.getKtStationsByKeyword(keyword));
    }

    @PostMapping("/getDataByDatetime")
    public ResponseEntity<?> getDataByDatetime(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.ktService.getKtDataByDatetime(request));
    }

    @PostMapping("/getDataByItem")
    public ResponseEntity<?> getDataByItem(@RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.ktService.getKtDataByItem(request));
    }
}
