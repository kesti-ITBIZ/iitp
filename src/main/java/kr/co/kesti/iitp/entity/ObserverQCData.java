package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.ObserverQCDataKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_OBSERVER_DATA_QC")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObserverQCData {
    @EmbeddedId
    private ObserverQCDataKey observerQCDataKey;

    @Column(name = "pm25_state_1")
    private Short pm25_state_1;

    @Column(name = "pm25_state_2")
    private Short pm25_state_2;

    @Column(name = "pm25_state_3")
    private Short pm25_state_3;

    @Column(name = "pm25_state_4")
    private Short pm25_state_4;

    @Column(name = "pm25_state_5")
    private Short pm25_state_5;

    @Column(name = "pm25_state")
    private Short pm25_state;

    @Column(name = "pm25_median", precision = 6, scale = 1)
    private Float pm25_median;

    @Column(name = "pm25_std", precision = 6, scale = 1)
    private Float pm25_std;

    @Column(name = "pm25_std_rolling", precision = 6, scale = 1)
    private Float pm25_std_rolling;

    @Column(name = "pm25_spatial_upper_bound", precision = 6, scale = 1)
    private Float pm25_spatial_upper_bound;

    @Column(name = "pm25_spatial_lower_bound", precision = 6, scale = 1)
    private Float pm25_spatial_lower_bound;

    @Column(name = "pm25_isna", length = 10)
    private String pm25_isna;

    @Column(name = "pm25_qc", precision = 6, scale = 1)
    private Float pm25_qc;

    @Column(name = "pm25_r_state_1")
    private Short pm25_r_state_1;

    @Column(name = "pm25_r_state_2")
    private Short pm25_r_state_2;

    @Column(name = "pm25_r_state_3")
    private Short pm25_r_state_3;

    @Column(name = "pm25_r_state_4")
    private Short pm25_r_state_4;

    @Column(name = "pm25_r_state_5")
    private Short pm25_r_state_5;

    @Column(name = "pm25_r_state")
    private Short pm25_r_state;

    @Column(name = "pm25_r_median", precision = 6, scale = 1)
    private Float pm25_r_median;

    @Column(name = "pm25_r_std", precision = 6, scale = 1)
    private Float pm25_r_std;

    @Column(name = "pm25_r_std_rolling", precision = 6, scale = 1)
    private Float pm25_r_std_rolling;

    @Column(name = "pm25_r_spatial_upper_bound", precision = 6, scale = 1)
    private Float pm25_r_spatial_upper_bound;

    @Column(name = "pm25_r_spatial_lower_bound", precision = 6, scale = 1)
    private Float pm25_r_spatial_lower_bound;

    @Column(name = "pm25_r_isna", length = 10)
    private String pm25_r_isna;

    @Column(name = "pm25_r_qc", precision = 6, scale = 1)
    private Float pm25_r_qc;

    @Column(name = "tmp_state_1")
    private Short tmp_state_1;

    @Column(name = "tmp_state_2")
    private Short tmp_state_2;

    @Column(name = "tmp_state_3")
    private Short tmp_state_3;

    @Column(name = "tmp_state_4")
    private Short tmp_state_4;

    @Column(name = "tmp_state_5")
    private Short tmp_state_5;

    @Column(name = "tmp_state")
    private Short tmp_state;

    @Column(name = "tmp_median", precision = 6, scale = 1)
    private Float tmp_median;

    @Column(name = "tmp_std", precision = 6, scale = 1)
    private Float tmp_std;

    @Column(name = "tmp_std_rolling", precision = 6, scale = 1)
    private Float tmp_std_rolling;

    @Column(name = "tmp_spatial_upper_bound", precision = 6, scale = 1)
    private Float tmp_spatial_upper_bound;

    @Column(name = "tmp_spatial_lower_bound", precision = 6, scale = 1)
    private Float tmp_spatial_lower_bound;

    @Column(name = "tmp_isna", length = 10)
    private String tmp_isna;

    @Column(name = "tmp_qc", precision = 6, scale = 1)
    private Float tmp_qc;

    @Column(name = "reh_state_1")
    private Short reh_state_1;

    @Column(name = "reh_state_2")
    private Short reh_state_2;

    @Column(name = "reh_state_3")
    private Short reh_state_3;

    @Column(name = "reh_state_4")
    private Short reh_state_4;

    @Column(name = "reh_state_5")
    private Short reh_state_5;

    @Column(name = "reh_state")
    private Short reh_state;

    @Column(name = "reh_median", precision = 6, scale = 1)
    private Float reh_median;

    @Column(name = "reh_std", precision = 6, scale = 1)
    private Float reh_std;

    @Column(name = "reh_std_rolling", precision = 6, scale = 1)
    private Float reh_std_rolling;

    @Column(name = "reh_spatial_upper_bound", precision = 6, scale = 1)
    private Float reh_spatial_upper_bound;

    @Column(name = "reh_spatial_lower_bound", precision = 6, scale = 1)
    private Float reh_spatial_lower_bound;

    @Column(name = "reh_isna", length = 10)
    private String reh_isna;

    @Column(name = "reh_qc", precision = 6, scale = 1)
    private Float reh_qc;

    @Column(name = "press", precision = 6, scale = 1)
    private Float press;

}
