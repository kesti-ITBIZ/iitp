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
public class ObserverDataKey implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_time")
    private Date dataTime;

    @Column(name = "stn_serial", length = 20)
    private String stnSerial;
}
