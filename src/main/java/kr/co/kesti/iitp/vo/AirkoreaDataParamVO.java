package kr.co.kesti.iitp.vo;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaDataParamVO {
    private String startDatetime;
    private String endDatetime;
    private String stnNm;
    private List<String> items;
    private List<Float> pm10;
    private List<Float> pm25;
}
