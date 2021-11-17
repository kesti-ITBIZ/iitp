package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AIRKOREA_STN_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirkoreaStation {
    @Column(name = "run_time", length = 30, nullable = false)
    private String runTime;

    @Id
    @Column(name = "stn_nm", length = 30)
    private String stnNm;

    @Column(name = "addr", length = 500, nullable = false)
    private String addr;

    @Column(name = "year", length = 4)
    private String year;

    @Column(name = "mang_nm", length = 100, nullable = false)
    private String mangNm;

    @Column(name = "item", length = 30, nullable = false)
    private String item;

    @Column(name = "lo", precision = 14, scale = 10, nullable = false)
    private Float longitude;

    @Column(name = "la", precision = 14, scale = 10, nullable = false)
    private Float latitude;
}
