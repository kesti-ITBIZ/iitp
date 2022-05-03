package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.AirkoreaPastDataKey;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AIRKOREA_DATA_FIX")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaPastData {
    @Column(name = "region", length = 30, nullable = false)
    private String region;

    @Type(type = "pg-uuid")
    @EmbeddedId
    private AirkoreaPastDataKey airkoreaPastDataKey;

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

    @Column(name = "pm25", precision = 6, scale = 1)
    private Float pm25;

    @Column(name = "mang_nm", length = 30, nullable = false)
    private String mangNm;

}
