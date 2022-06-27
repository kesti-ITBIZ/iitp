package kr.co.kesti.iitp.embed;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaPastDataKey implements Serializable {
    @Column(name = "stn_code", length = 30)
    private String stnCode;

    @Column(name = "stn_nm", length = 30)
    private String stnNm;

    @Column(name = "data_datetime", length = 30)
    private String dataDatetime;
}
