package kr.co.kesti.iitp.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import kr.co.kesti.iitp.embed.SDoTDataKey;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "TB_SDOT_DATA_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SDoTData {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "run_time", length = 30, nullable = false)
    private String runTime;

    @EmbeddedId
    private SDoTDataKey sDoTDataKey;

    @Column(name = "organ_nm", length = 30, nullable = false)
    private String organNm;

    @Column(name = "transmit_server_no", nullable = false)
    private Short transmitServerNo;

    @Column(name = "data_no", nullable = false)
    private Short dataNo;

    @Column(name = "model_nm", length = 30, nullable = false)
    private String modelNm;

    @Column(name = "pm25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "pm10", precision = 6, scale = 1)
    private Float pm10;

    @Column(name = "temp", precision = 6, scale = 1)
    private Float temperature;

    @Column(name = "rhd", precision = 6, scale = 1)
    private Float relativeHumidity;

    @Column(name = "w_dir", precision = 6, scale = 1)
    private Float windDirection;

    @Column(name = "w_spd", precision = 6, scale = 1)
    private Float windSpeed;

    @Column(name = "gw_dir", precision = 6, scale = 1)
    private Float gwDir;

    @Column(name = "gw_spd", precision = 6, scale = 1)
    private Float gwSpd;

    @Column(name = "lux", precision = 6, scale = 1)
    private Float lux;

    @Column(name = "uv", precision = 6, scale = 1)
    private Float uv;

    @Column(name = "noise", precision = 6, scale = 1)
    private Float noise;

    @Column(name = "vib_x", precision = 6, scale = 1)
    private Float vibX;

    @Column(name = "vib_y", precision = 6, scale = 1)
    private Float vibY;

    @Column(name = "vib_z", precision = 6, scale = 1)
    private Float vibZ;

    @Column(name = "maxvib_x", precision = 6, scale = 1)
    private Float maxvibX;

    @Column(name = "maxvib_y", precision = 6, scale = 1)
    private Float maxvibY;

    @Column(name = "maxvib_z", precision = 6, scale = 1)
    private Float maxvibZ;

    @Column(name = "bbtemp", precision = 6, scale = 1)
    private Float bbtemp;

    @Column(name = "r_pm25", precision = 6, scale = 1)
    private Float rPm25;

    @Column(name = "r_pm10", precision = 6, scale = 1)
    private Float rPm10;
}
