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

    public ResponseKTDataVO(String stnNm, Float temperature, Float humidity, Float pm10, Float pm25) {
        this.stnNm = stnNm;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }
}
