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
public class AirkoreaDataKey implements Serializable {
    @Column(name = "TIME", length = 30)
    private String time;

    @Column(name = "STN_NM", length = 30)
    private String stnNm;
}
