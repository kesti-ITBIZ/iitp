package kr.co.kesti.iitp.vo;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataVO {
    private String startDatetime;
    private String endDatetime;
    private String dateType;
    private String stnNm;
    private List<Float> pm10;
    private List<Float> pm25;
}
