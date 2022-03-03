package kr.co.kesti.iitp.controller;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
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
//@Api(tags = "고농도일수 통계 API")
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

//    @ApiOperation(value = "지점 조회")
    @GetMapping("/stations")
    public ResponseEntity<?> getStations(
//            @ApiParam(
//                    value = "데이터 기관",
//                    required = true,
//                    example = "airkorea")
            @RequestParam("category")
            final String category,
//            @ApiParam(
//                    value = "지점 조회 시간 - 시작",
//                    required = true,
//                    example = "202110")
            @RequestParam("startDatetime")
            final String startDatetime,
//            @ApiParam(
//                    value = "지점 조회 시간 - 끝",
//                    required = true,
//                    example = "202203")
            @RequestParam("endDatetime")
            final String endDatetime) {
        return ResponseEntity.ok(this.statisticsService.getStations(category, startDatetime, endDatetime));
    }

//    @ApiOperation(value = "고농도 일수 통계 데이터 조회")
    @GetMapping("/getData")
    public ResponseEntity<?> getData(
//            @ApiParam(
//                    value = "데이터 기관",
//                    required = true,
//                    example = "kt")
            @RequestParam("category")
            final String category,
//            @ApiParam(
//                    value = "지점 조회 시간 - 시작",
//                    required = true,
//                    example = "201807")
            @RequestParam("startDatetime")
            final String startDatetime,
//            @ApiParam(
//                    value = "지점 조회 시간 - 끝",
//                    required = true,
//                    example = "201912")
            @RequestParam("endDatetime")
            final String endDatetime,
//            @ApiParam(
//                    value = "조회 지점명",
//                    required = true,
//                    example = "화곡동 양꼬치가게앞")
            @RequestParam("stnNm")
            final String stnNm) {
        return ResponseEntity.ok(this.statisticsService.getStatisticsData(category, startDatetime, endDatetime, stnNm));
    }
}
