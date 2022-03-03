package kr.co.kesti.iitp.controller;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import kr.co.kesti.iitp.service.KTService;
import kr.co.kesti.iitp.vo.RequestDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
//@Api(tags = "KT API")
@RestController
@RequestMapping("/api/kt")
public class KTController {
    private final KTService ktService;

//    @ApiOperation(value = "키워드 기반 KT 지점 검색")
    @GetMapping("/searchStations")
    public ResponseEntity<?> searchStations(
//            @ApiParam(
//                    value = "검색 키워드",
//                    required = true,
//                    example = "대")
            @RequestParam("keyword")
            final String keyword,
//            @ApiParam(
//                    value = "현재 페이지",
//                    required = true,
//                    example = "0")
            @RequestParam("page")
            final int page,
//            @ApiParam(
//                    value = "한 페이지 크기",
//                    required = true,
//                    example = "100")
            @RequestParam("size")
            final int size) {
        return ResponseEntity.ok(this.ktService.getKtStationsByKeyword(keyword, page, size));
    }

//    @ApiOperation(value = "KT 관측정보 데이터 조회")
    @PostMapping("/getData")
    public ResponseEntity<?> getData(
//            @ApiParam(
//                    value = "KT 관측정보 데이터 조회 API 파라미터",
//                    required = true)
            @RequestBody final RequestDataVO request) {
        return ResponseEntity.ok(this.ktService.getKtData(request));
    }

//    @ApiOperation(value = "조회 가능한 KT 데이터 조회 기간 조회")
    @GetMapping("/getAvailableDatetimes")
    public ResponseEntity<?> getAvailableDatetimes() {
        return ResponseEntity.ok(this.ktService.getKtAvailableDatetimes());
    }
}
