package kr.co.kesti.iitp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        value = "관측정보 데이터 조회 API 파라미터",
        description = "조회 기간 중 시작, 끝 시간과 지점명을 포함한다.")
public class RequestDataVO {
    @ApiModelProperty(value = "데이터 조회 시간 - 시작", required = true)
    private String startDatetime;
    @ApiModelProperty(value = "데이터 조회 시간 - 끝", required = true)
    private String endDatetime;
    @ApiModelProperty(value = "조회지점명", required = true)
    private String stnNm;
}
