package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.AirkoreaDataKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AIRKOREA_DATA_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaData {
    @Column(name = "run_time", length = 30, nullable = false)
    private String runTime;

    @EmbeddedId
    private AirkoreaDataKey airkoreaDataKey;

    @Column(name = "mang_nm", length = 30, nullable = false)
    private String mangNm;

    @Column(name = "sido_nm", length = 30, nullable = false)
    private String sidoNm;

    @Column(name = "so2", precision = 12, scale = 6)
    private Float so2;

    @Column(name = "co", precision = 12, scale = 6)
    private Float co;

    @Column(name = "o3", precision = 12, scale = 6)
    private Float o3;

    @Column(name = "no2", precision = 12, scale = 6)
    private Float no2;

    @Column(name = "pm10", precision = 6, scale = 1)
    private Float pm10;

    @Column(name = "pm10_24h", precision = 6, scale = 1)
    private Float pm10_24h;

    @Column(name = "pm25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "pm25_24h", precision = 6, scale = 1)
    private Float pm25_24h;

    @Column(name = "khai", precision = 6, scale = 1)
    private Float khai;

    @Column(name = "khai_grd")
    private Short khaiGrd;

    @Column(name = "so2_grd")
    private Short so2Grd;

    @Column(name = "co_grd")
    private Short coGrd;

    @Column(name = "o3_grd")
    private Short o3_Grd;

    @Column(name = "no2_grd")
    private Short no2_Grd;

    @Column(name = "pm10_grd")
    private Short pm10_Grd;

    @Column(name = "pm25_grd")
    private Short pm25_Grd;

    @Column(name = "pm10_grd_1h")
    private Short pm10_Grd1h;

    @Column(name = "pm25_grd_1h")
    private Short pm25_Grd1h;

    @Column(name = "so2_flag", length = 10)
    private String so2_Flag;

    @Column(name = "co_flag", length = 10)
    private String coFlag;

    @Column(name = "o3_flag", length = 10)
    private String o3_Flag;

    @Column(name = "no2_flag", length = 10)
    private String no2_Flag;

    @Column(name = "pm10_flag", length = 10)
    private String pm10_Flag;

    @Column(name = "pm25_flag", length = 10)
    private String pm25_Flag;
}
