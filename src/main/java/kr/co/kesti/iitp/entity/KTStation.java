package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_KT_STN_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KTStation {
    @Id
    @Column(name = "DEV_ID", length = 20)
    private String devId;

    @Column(name = "DEV_NM", length = 100)
    private String devNm;

    @Column(name = "ADDR", length = 500)
    private String addr;

    @Column(name = "NEWADDR", length = 500)
    private String newaddr;

    @Column(name = "LO", precision = 14, scale = 10)
    private Float lo;

    @Column(name = "LA", precision = 14, scale = 10)
    private Float la;

    @Column(name = "REGIST_TIME", length = 20)
    private String registTime;
}
