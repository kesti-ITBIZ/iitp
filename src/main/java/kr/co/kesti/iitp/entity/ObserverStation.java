package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_OBSERVER_STN_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObserverStation {
    @Id
    @Column(name = "stn_serial", length = 20)
    private String stnSerial;

    @Column(name = "stn_nm", length = 10, nullable = false)
    private String stnNm;

    @Column(name = "addr", length = 500, nullable = false)
    private String addr;

    @Column(name = "lo", precision = 14, scale = 10, nullable = false)
    private Float lo;

    @Column(name = "la", precision = 14, scale = 10, nullable = false)
    private Float la;

    @Column(name = "regist_time", length = 20, nullable = false)
    private String registTime;
}
