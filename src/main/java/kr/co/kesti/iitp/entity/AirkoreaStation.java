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
    @Column(name = "RUN_TIME", length = 30, nullable = false)
    private String runTime;

    @Id
    @Column(name = "STN_NM", length = 30)
    private String stnNm;

    @Column(name = "ADDR", length = 500, nullable = false)
    private String addr;

    @Column(name = "YEAR", length = 4)
    private String year;

    @Column(name = "MANG_NM", length = 100, nullable = false)
    private String mangNm;

    @Column(name = "ITEM", length = 30, nullable = false)
    private String item;

    @Column(name = "LO", precision = 14, scale = 10, nullable = false)
    private Float lo;

    @Column(name = "LA", precision = 14, scale = 10, nullable = false)
    private Float la;
}
