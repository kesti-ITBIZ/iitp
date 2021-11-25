package kr.co.kesti.iitp.vo;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObserverDataVO {
    private String datetime;
    private String stnNm;
    private Float temperature;
    private Float humidity;
    private Float pressure;
    private Float pm25;

    public ResponseObserverDataVO(String stnNm, Float temperature, Float humidity, Float pressure, Float pm25) {
        this.stnNm = stnNm;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.pm25 = pm25;
    }
}
