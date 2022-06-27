package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.SDoTQCDataKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TB_SDOT_DATA_QC")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SDoTQCData {

    @EmbeddedId
    private SDoTQCDataKey sDoTQCDataKey;

    @Column(name = "pm10", precision = 6, scale = 1)
    private Float pm10;

    @Column(name = "pm25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "tmp", precision = 6, scale = 1)
    private Float tmp;

    @Column(name = "reh", precision = 6, scale = 1)
    private Float reh;

    @Column(name = "pm10_state_1")
    private Short pm10_state_1;

    @Column(name = "pm25_state_1")
    private Short pm25_state_1;

    @Column(name = "tmp_state_1")
    private Short tmp_state_1;

    @Column(name = "reh_state_1")
    private Short reh_state_1;

    @Column(name = "pm10_state_2")
    private Short pm10_state_2;

    @Column(name = "pm25_state_2")
    private Short pm25_state_2;

    @Column(name = "tmp_state_2")
    private Short tmp_state_2;

    @Column(name = "reh_state_2")
    private Short reh_state_2;

    @Column(name = "pm10_state_3")
    private Short pm10_state_3;

    @Column(name = "pm25_state_3")
    private Short pm25_state_3;

    @Column(name = "tmp_state_3")
    private Short tmp_state_3;

    @Column(name = "reh_state_3")
    private Short reh_state_3;

    @Column(name = "pm10_state_4")
    private Short pm10_state_4;

    @Column(name = "pm25_state_4")
    private Short pm25_state_4;

    @Column(name = "tmp_state_4")
    private Short tmp_state_4;

    @Column(name = "reh_state_4")
    private Short reh_state_4;

    @Column(name = "pm10_median", precision = 6, scale = 1)
    private Float pm10_median;

    @Column(name = "pm10_std", precision = 6, scale = 1)
    private Float pm10_std;

    @Column(name = "pm10_state_5")
    private Short pm10_state_5;

    @Column(name = "pm25_median", precision = 6, scale = 1)
    private Float pm25_median;

    @Column(name = "pm25_std", precision = 6, scale = 1)
    private Float pm25_std;

    @Column(name = "pm25_state_5")
    private Short pm25_state_5;

    @Column(name = "tmp_median", precision = 6, scale = 1)
    private Float tmp_median;

    @Column(name = "tmp_std", precision = 6, scale = 1)
    private Float tmp_std;

    @Column(name = "tmp_state_5")
    private Short tmp_state_5;

    @Column(name = "reh_median", precision = 6, scale = 1)
    private Float reh_median;

    @Column(name = "reh_std", precision = 6, scale = 1)
    private Float reh_std;

    @Column(name = "reh_state_5")
    private Short reh_state_5;

    @Column(name = "pm10_std_rolling", precision = 6, scale = 1)
    private Float pm10_std_rolling;

    @Column(name = "pm10_spatial_upper_bound", precision = 6, scale = 1)
    private Float pm10_spatial_upper_bound;

    @Column(name = "pm10_spatial_lower_bound", precision = 6, scale = 1)
    private Float pm10_spatial_lower_bound;

    @Column(name = "pm25_std_rolling", precision = 6, scale = 1)
    private Float pm25_std_rolling;

    @Column(name = "pm25_spatial_upper_bound", precision = 6, scale = 1)
    private Float pm25_spatial_upper_bound;

    @Column(name = "pm25_spatial_lower_bound", precision = 6, scale = 1)
    private Float pm25_spatial_lower_bound;

    @Column(name = "tmp_std_rolling", precision = 6, scale = 1)
    private Float tmp_std_rolling;

    @Column(name = "tmp_spatial_upper_bound", precision = 6, scale = 1)
    private Float tmp_spatial_upper_bound;

    @Column(name = "tmp_spatial_lower_bound", precision = 6, scale = 1)
    private Float tmp_spatial_lower_bound;

    @Column(name = "reh_std_rolling", precision = 6, scale = 1)
    private Float reh_std_rolling;

    @Column(name = "reh_spatial_upper_bound", precision = 6, scale = 1)
    private Float reh_spatial_upper_bound;

    @Column(name = "reh_spatial_lower_bound", precision = 6, scale = 1)
    private Float reh_spatial_lower_bound;

    @Column(name = "pm10_state")
    private Short pm10_state;

    @Column(name = "pm25_state")
    private Short pm25_state;

    @Column(name = "tmp_state")
    private Short tmp_state;

    @Column(name = "reh_state")
    private Short reh_state;

    @Column(name = "pm10_isna", length = 10)
    private String pm10_isna;

    @Column(name = "pm10_qc", precision = 6, scale = 1)
    private Float pm10_qc;

    @Column(name = "pm25_isna", length = 10)
    private String pm25_isna;

    @Column(name = "pm25_qc", precision = 6, scale = 1)
    private Float pm25_qc;

    @Column(name = "tmp_isna", length = 10)
    private String tmp_isna;

    @Column(name = "tmp_qc", precision = 6, scale = 1)
    private Float tmp_qc;

    @Column(name = "reh_isna", length = 10)
    private String reh_isna;

    @Column(name = "reh_qc", precision = 6, scale = 1)
    private Float reh_qc;

    @Column(name = "run_time", length = 30)
    private String run_time;

    @Column(name = "w_dir", precision = 6, scale = 1)
    private Float w_dir;

    @Column(name = "w_spd", precision = 6, scale = 1)
    private Float w_spd;
}
