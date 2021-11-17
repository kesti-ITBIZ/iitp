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

    @Column(name = "lo", precision = 14, scale = 10)
    private Float longitude;

    @Column(name = "la", precision = 14, scale = 10)
    private Float latitude;

    @Column(name = "press", precision = 6, scale = 1)
    private Float pressure;

    @Column(name = "temp", precision = 6, scale = 1)
    private Float temperature;

    @Column(name = "hum", precision = 6, scale = 1)
    private Float humidity;

    @Column(name = "qc_press", length = 5)
    private String qcPressure;

    @Column(name = "qc_temp", length = 5)
    private String qcTemperature;

    @Column(name = "qc_hum", length = 5)
    private String qcHumidity;

    @Column(name = "pm25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "pm25_r", precision = 8, scale = 3)
    private Float pm25_R;

    @Column(name = "qc_pm25", length = 5)
    private String qcPm25;

    @Column(name = "pty", precision = 6, scale = 1)
    private Float pty;

    @Column(name = "pty_r", length = 10)
    private String ptyR;
}
