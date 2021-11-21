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
}
