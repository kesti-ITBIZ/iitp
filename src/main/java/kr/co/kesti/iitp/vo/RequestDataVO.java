package kr.co.kesti.iitp.vo;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataVO {
    private String startDatetime;
    private String endDatetime;
    private String stnNm;
}
