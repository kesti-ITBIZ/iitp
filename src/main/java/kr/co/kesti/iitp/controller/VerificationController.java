package kr.co.kesti.iitp.controller;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import kr.co.kesti.iitp.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
//@Api(tags = "비교분석 API")
@RestController
@RequestMapping("/api/verification")
public class VerificationController {
    private final VerificationService verificationService;

//    @ApiOperation(value = "비교분석 데이터 조회")
    @GetMapping("/getData")
    public ResponseEntity<?> getData(
//            @ApiParam(
//                    value = "기준지점의 데이터 제공 기관",
//                    required = true,
//                    example = "airkorea")
            @RequestParam("standard")
            final String standard,
//            @ApiParam(
//                    value = "비교지점의 데이터 제공 기관",
//                    required = true,
//                    example = "sDoT")
            @RequestParam("compare")
            final String compare,
//            @ApiParam(
//                    value = "데이터 조회 시간 - 시작",
//                    required = true,
//                    example = "2021-11-12 00:00")
            @RequestParam("startDatetime")
            final String startDatetime,
//            @ApiParam(
//                    value = "데이터 조회 시간 - 끝",
//                    required = true,
//                    example = "2021-11-21 23:59")
            @RequestParam("endDatetime")
            final String endDatetime,
            @Nullable
//            @ApiParam(value = "기준지점 ID")
            @RequestParam("stdStnId")
            final String stdStnId,
            @Nullable
//            @ApiParam(
//                    value = "기준지점명",
//                    example = "강남구")
            @RequestParam("stdStnNm")
            final String stdStnNm,
            @Nullable
//            @ApiParam(
//                    value = "비교지점 ID",
//                    example = "OC3CL200023")
            @RequestParam("compStnId")
            final String compStnId,
            @Nullable
//            @ApiParam(value = "비교지점명")
            @RequestParam("compStnNm")
            final String compStnNm) {
        return ResponseEntity.ok(this.verificationService.getVerificationData(standard, compare, startDatetime, endDatetime, stdStnId, stdStnNm, compStnId, compStnNm));
    }
}
