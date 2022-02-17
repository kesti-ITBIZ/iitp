package kr.co.kesti.iitp.vo;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatisticsDataVO {
    private String category;
    private String datetime;
    private String stnNm;
    private Value pm10;
    private Value pm25;

    @Data
    @With
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Value {
        private Float avg;
        private Float goodDays;
        private Float badDays;
        private Float highDnstyDays;
    }
}
