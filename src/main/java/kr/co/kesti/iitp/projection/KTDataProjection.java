package kr.co.kesti.iitp.projection;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KTDataProjection {
    private String datetime;
    private String stnNm;
    private Float temperature;
    private Float humidity;
    private Float pm10;
    private Float pm25;
}
