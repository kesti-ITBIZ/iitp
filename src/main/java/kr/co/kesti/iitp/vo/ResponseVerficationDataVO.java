package kr.co.kesti.iitp.vo;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVerficationDataVO {
    private String datetime;
    private String stdStnId;
    private String stdStnNm;
    private Float stdPm10;
    private Float stdPm25;
    private String compStnId;
    private String compStnNm;
    private Float compPm10;
    private Float compPm25;
}
