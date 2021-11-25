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

    public ResponseAirkoreaDataVO(String stnNm, Float so2, Float co, Float o3, Float no2, Float pm10, Float pm25) {
        this.stnNm = stnNm;
        this.so2 = so2;
        this.co = co;
        this.o3 = o3;
        this.no2 = no2;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }
}
