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
public class KTDataKey implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "equip_date")
    private Date equipDate;

    @Column(name = "dev_id", length = 20)
    private String devId;
}
