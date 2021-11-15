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
    @Column(name = "RUN_TIME", length = 30, nullable = false)
    private String runTime;

    @EmbeddedId
    private AirkoreaDataKey airkoreaDataKey;

    @Column(name = "MANG_NM", length = 30, nullable = false)
    private String mangNm;

    @Column(name = "SIDO_NM", length = 30, nullable = false)
    private String sidoNm;

    @Column(name = "SO2", precision = 12, scale = 6)
    private Float so2;

    @Column(name = "CO", precision = 12, scale = 6)
    private Float co;

    @Column(name = "O3", precision = 12, scale = 6)
    private Float o3;

    @Column(name = "NO2", precision = 12, scale = 6)
    private Float no2;

    @Column(name = "PM10", precision = 6, scale = 1)
    private Float pm10;

    @Column(name = "PM10_24H", precision = 6, scale = 1)
    private Float pm10_24h;

    @Column(name = "PM25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "PM25_24H", precision = 6, scale = 1)
    private Float pm25_24h;

    @Column(name = "KHAI", precision = 6, scale = 1)
    private Float khai;

    @Column(name = "KHAI_GRD")
    private Long khaiGrd;

    @Column(name = "SO2_GRD")
    private Long so2Grd;

    @Column(name = "CO_GRD")
    private Long coGrd;

    @Column(name = "O3_GRD")
    private Long o3_Grd;

    @Column(name = "NO2_GRD")
    private Long no2_Grd;

    @Column(name = "PM10_GRD")
    private Long pm10_Grd;

    @Column(name = "PM25_GRD")
    private Long pm25_Grd;

    @Column(name = "PM10_GRD_1H")
    private Long pm10_Grd1h;

    @Column(name = "PM25_GRD_1H")
    private Long pm25_Grd1h;

    @Column(name = "SO2_FLAG", length = 10)
    private String so2_Flag;

    @Column(name = "CO_FLAG", length = 10)
    private String coFlag;

    @Column(name = "O3_FLAG", length = 10)
    private String o3_Flag;

    @Column(name = "NO2_FLAG", length = 10)
    private String no2_Flag;

    @Column(name = "PM10_FLAG", length = 10)
    private String pm10_Flag;

    @Column(name = "PM25_FLAG", length = 10)
    private String pm25_Flag;
}
