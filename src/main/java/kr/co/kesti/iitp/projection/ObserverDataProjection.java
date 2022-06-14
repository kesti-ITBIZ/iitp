package kr.co.kesti.iitp.projection;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObserverDataProjection {
    private String datetime;
    private String stnNm;
    private Float temperature;
    private Float humidity;
    private Float pm25;
}
