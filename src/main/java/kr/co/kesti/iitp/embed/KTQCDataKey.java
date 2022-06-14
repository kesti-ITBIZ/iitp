package kr.co.kesti.iitp.embed;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KTQCDataKey implements Serializable {
    @Column(name = "time", length = 30)
    private String time;

    @Column(name = "stn_id", length = 30)
    private String stnId;
}
