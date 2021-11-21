package kr.co.kesti.iitp.vo;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseKTDataVO {
    private String datetime;
    private String stnNm;
    private Float temperature;
    private Float humidity;
    private Float pm10;
    private Float pm25;
}
