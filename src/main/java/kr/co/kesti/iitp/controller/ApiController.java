package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class ApiController {
    private final ApiService apiService;

    private final RestTemplate restTemplate;


    @GetMapping("/kakaoMap")
    public String kakaoMap() {
        final String js = this.restTemplate.getForObject("http://dapi.kakao.com/v2/maps/sdk.js?appkey=974e50208137daaf6a2f9b0803164bea&libraries=services,clusterer,drawing", String.class);
        return js;
    }

    @GetMapping("/stationsAll")
    public ResponseEntity<?> stationsAll() {
        return ResponseEntity.ok(this.apiService.stationsAll());
    }

    @GetMapping("/stations/{category}")
    public ResponseEntity<?> stations(@PathVariable final String category) {
        return ResponseEntity.ok(this.apiService.stations(category));
    }
}
