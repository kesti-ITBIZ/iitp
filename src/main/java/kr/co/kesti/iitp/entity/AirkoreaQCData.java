package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.AirkoreaQCDataKey;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AIRKOREA_DATA_QC")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaQCData {

    @Type(type = "pg-uuid")
    @EmbeddedId
    private AirkoreaQCDataKey airkoreaQCDataKey;

    @Column(name = "pm10", precision = 6, scale = 1)
    private Float pm10;

    @Column(name = "pm25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "pm10_state_1")
    private Short pm10_state_1;

    @Column(name = "pm25_state_1")
    private Short pm25_state_1;

    @Column(name = "pm10_state_2")
    private Short pm10_state_2;

    @Column(name = "pm25_state_2")
    private Short pm25_state_2;

    @Column(name = "pm10_state_3")
    private Short pm10_state_3;

    @Column(name = "pm25_state_3")
    private Short pm25_state_3;

    @Column(name = "pm10_state_4")
    private Short pm10_state_4;

    @Column(name = "pm25_state_4")
    private Short pm25_state_4;

    @Column(name = "pm10_median", precision = 6, scale = 1)
    private Float pm10_median;

    @Column(name = "pm10_std", precision = 6, scale = 1)
    private Float pm10_std;

    @Column(name = "pm25_median", precision = 6, scale = 1)
    private Float pm25_median;

    @Column(name = "pm25_std", precision = 6, scale = 1)
    private Float pm25_std;

    @Column(name = "pm10_std_rolling", precision = 6, scale = 1)
    private Float pm10_std_rolling;

    @Column(name = "pm10_spatial_upper_bound", precision = 6, scale = 1)
    private Float pm10_spatial_upper_bound;

    @Column(name = "pm10_spatial_lower_bound", precision = 6, scale = 1)
    private Float pm10_spatial_lower_bound;

    @Column(name = "pm10_state_5")
    private Short pm10_state_5;

    @Column(name = "pm25_std_rolling", precision = 6, scale = 1)
    private Float pm25_std_rolling;

    @Column(name = "pm25_spatial_upper_bound", precision = 6, scale = 1)
    private Float pm25_spatial_upper_bound;

    @Column(name = "pm25_spatial_lower_bound", precision = 6, scale = 1)
    private Float pm25_spatial_lower_bound;

    @Column(name = "pm25_state_5")
    private Short pm25_state_5;

    @Column(name = "pm10_state")
    private Short pm10_state;

    @Column(name = "pm25_state")
    private Short pm25_state;

    @Column(name = "pm10_isna", length = 10)
    private String pm10_isna;

    @Column(name = "pm10_qc", precision = 6, scale = 1)
    private Float pm10_qc;

    @Column(name = "pm25_isna", length = 10)
    private String pm25_isna;

    @Column(name = "pm25_qc", precision = 6, scale = 1)
    private Float pm25_qc;

    @Column(name = "run_time", length = 30)
    private String run_time;

    @Column(name = "so2", precision = 12, scale = 6)
    private Float so2;

    @Column(name = "co", precision = 12, scale = 6)
    private Float co;

    @Column(name = "o3", precision = 12, scale = 6)
    private Float o3;

    @Column(name = "no2", precision = 12, scale = 6)
    private Float no2;

}
