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
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_DATE")
    private Date dataDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "DATA_TIME")
    private Date dataTime;

    @Column(name = "STN_SERIAL", length = 20)
    private String stnSerial;
}
