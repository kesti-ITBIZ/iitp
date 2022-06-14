package kr.co.kesti.iitp.vo;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSDoTDataVO {
    private String datetime;
    private String stnNm;
    private Float temperature;
    private Float relativeHumidity;
    private Float pm10;
    private Float pm25;

    public ResponseSDoTDataVO(String stnNm, Float temperature, Float relativeHumidity, Float pm10, Float pm25) {
        this.stnNm = stnNm;
        this.temperature = temperature;
        this.relativeHumidity = relativeHumidity;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }
}
