package kr.co.kesti.iitp.projection;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SDoTDataProjection {
    private String datetime;
    private String stnNm;
    private Float temperature;
    private Float relativeHumidity;
    private Float windDirection;
    private Float windSpeed;
    private Float pm10;
    private Float pm25;
}
