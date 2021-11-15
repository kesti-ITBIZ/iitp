package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.ObserverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/observer")
public class ObserverController {
    private final ObserverService observerService;

    @GetMapping("/stations")
    public ResponseEntity<?> findAllStations() {
        return ResponseEntity.ok(this.observerService.findAllStations());
    }
}
