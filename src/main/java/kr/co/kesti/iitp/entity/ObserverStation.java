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
    @Column(name = "STN_SERIAL", length = 20)
    private String stnSerial;

    @Column(name = "STN_NM", length = 10, nullable = false)
    private String stnNm;

    @Column(name = "ADDR", length = 500, nullable = false)
    private String addr;

    @Column(name = "LO", precision = 14, scale = 10, nullable = false)
    private Float lo;

    @Column(name = "LO", precision = 14, scale = 10, nullable = false)
    private Float la;

    @Column(name = "REGIST_TIME", length = 20, nullable = false)
    private String registTime;
}
