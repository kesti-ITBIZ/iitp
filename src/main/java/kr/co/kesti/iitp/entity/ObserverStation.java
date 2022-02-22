package kr.co.kesti.iitp.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ObserverStation {
    @Id
    @Column(name = "stn_serial", length = 20)
    private String stnSerial;

    @Column(name = "stn_nm", length = 10, nullable = false)
    private String stnNm;

    @Column(name = "addr", length = 500, nullable = false)
    private String address;

    @Column(name = "lo", precision = 14, scale = 10, nullable = false)
    private Float longitude;

    @Column(name = "la", precision = 14, scale = 10, nullable = false)
    private Float latitude;

    @Column(name = "regist_time", length = 20, nullable = false)
    private String registTime;
}
