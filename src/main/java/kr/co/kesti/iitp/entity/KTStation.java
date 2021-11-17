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
    @Column(name = "dev_id", length = 20)
    private String devId;

    @Column(name = "dev_nm", length = 100)
    private String devNm;

    @Column(name = "addr", length = 500)
    private String address;

    @Column(name = "newaddr", length = 500)
    private String newAddress;

    @Column(name = "lo", precision = 14, scale = 10)
    private Float longitude;

    @Column(name = "la", precision = 14, scale = 10)
    private Float latitude;

    @Column(name = "regist_time", length = 20)
    private String registTime;
}
