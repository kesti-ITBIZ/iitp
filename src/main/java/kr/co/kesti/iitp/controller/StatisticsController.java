package kr.co.kesti.iitp.controller;

import kr.co.kesti.iitp.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/getData")
    public ResponseEntity<?> getData(
            @RequestParam("category") final String category,
            @RequestParam("startDatetime") final String startDatetime,
            @RequestParam("endDatetime") final String endDatetime,
            @RequestParam("stnNm") final String stnNm) {
        return ResponseEntity.ok(this.statisticsService.getStatisticsData(category, startDatetime, endDatetime, stnNm));
    }
}
