package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/stations/{category}")
    public ResponseEntity<?> stations(@PathVariable final String category) {
        return ResponseEntity.ok(this.apiService.stations(category));
    }
}
