package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.SDoTDataKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TB_SDOT_DATA_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SDoTData {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "RUN_TIME", length = 30, nullable = false)
    private String runTime;

    @EmbeddedId
    private SDoTDataKey sDoTDataKey;

    @Column(name = "ORGAN_NM", length = 30, nullable = false)
    private String organNm;

    @Column(name = "TRANSMIT_SERVER_NO", nullable = false)
    private Long transmitServerNo;

    @Column(name = "DATA_NO", nullable = false)
    private Long dataNo;

    @Column(name = "MODEL_NM", length = 30, nullable = false)
    private String modelNm;

    @Column(name = "PM25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "PM10", precision = 6, scale = 1)
    private Float pm10;

    @Column(name = "TEMP", precision = 6, scale = 1)
    private Float temp;

    @Column(name = "RHD", precision = 6, scale = 1)
    private Float rhd;

    @Column(name = "W_DIR", precision = 6, scale = 1)
    private Float wDir;

    @Column(name = "W_SPD", precision = 6, scale = 1)
    private Float wSpd;

    @Column(name = "GW_DIR", precision = 6, scale = 1)
    private Float gwDir;

    @Column(name = "GW_SPD", precision = 6, scale = 1)
    private Float gwSpd;

    @Column(name = "LUX", precision = 6, scale = 1)
    private Float lux;

    @Column(name = "UV", precision = 6, scale = 1)
    private Float uv;

    @Column(name = "NOISE", precision = 6, scale = 1)
    private Float noise;

    @Column(name = "VIB_X", precision = 6, scale = 1)
    private Float vibX;

    @Column(name = "VIB_Y", precision = 6, scale = 1)
    private Float vibY;

    @Column(name = "VIB_Z", precision = 6, scale = 1)
    private Float vibZ;

    @Column(name = "MAXVIB_X", precision = 6, scale = 1)
    private Float maxvibX;

    @Column(name = "MAXVIB_Y", precision = 6, scale = 1)
    private Float maxvibY;

    @Column(name = "MAXVIB_Z", precision = 6, scale = 1)
    private Float maxvibZ;

    @Column(name = "BBTEMP", precision = 6, scale = 1)
    private Float bbtemp;

    @Column(name = "R_PM25", precision = 6, scale = 1)
    private Float rPm25;

    @Column(name = "R_PM10", precision = 6, scale = 1)
    private Float rPm10;
}
