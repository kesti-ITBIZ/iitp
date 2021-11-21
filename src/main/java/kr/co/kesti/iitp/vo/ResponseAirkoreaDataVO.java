package kr.co.kesti.iitp.vo;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAirkoreaDataVO {
    private String datetime;
    private String stnNm;
    private Float so2;
    private Float co;
    private Float o3;
    private Float no2;
    private Float pm10;
    private Float pm25;
}
