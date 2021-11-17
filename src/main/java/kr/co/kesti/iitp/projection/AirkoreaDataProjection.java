package kr.co.kesti.iitp.projection;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaDataProjection {
    private String datetime;
    private String stnNm;
    private Float so2;
    private Float co;
    private Float o3;
    private Float no2;
    private Float pm10;
    private Float pm25;
}
