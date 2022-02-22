package kr.co.kesti.iitp.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_SDOT_STN_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SDoTStation implements Serializable {
    @Id
    @Column(name = "stn_id", length = 30)
    private String stnId;

    @Column(name = "addr", length = 500, nullable = false)
    private String address;

    @Column(name = "year", length = 4, nullable = false)
    private String year;

    @Column(name = "height", precision = 5, scale = 2)
    private Float height;

    @Column(name = "lo", precision = 14, scale = 10)
    private Float longitude;

    @Column(name = "la", precision = 14, scale = 10)
    private Float latitude;
}
