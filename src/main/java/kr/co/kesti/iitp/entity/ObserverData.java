package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.ObserverDataKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_OBSERVER_DATA_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObserverData {
    @EmbeddedId
    private ObserverDataKey observerDataKey;

    @Column(name = "LO", precision = 14, scale = 10)
    private Float lo;

    @Column(name = "LA", precision = 14, scale = 10)
    private Float la;

    @Column(name = "PRESS", precision = 6, scale = 1)
    private Float press;

    @Column(name = "TEMP", precision = 6, scale = 1)
    private Float temp;

    @Column(name = "HUM", precision = 6, scale = 1)
    private Float hum;

    @Column(name = "QC_PRESS", length = 5)
    private String qcPress;

    @Column(name = "QC_TEMP", length = 5)
    private String qcTemp;

    @Column(name = "QC_HUM", length = 5)
    private String qcHum;

    @Column(name = "PM25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "PM25_R", precision = 8, scale = 3)
    private Float pm25_R;

    @Column(name = "QC_PM25", length = 5)
    private String qcPm25;

    @Column(name = "PTY", precision = 6, scale = 1)
    private Float pty;

    @Column(name = "PTY_R", length = 10)
    private String ptyR;
}
