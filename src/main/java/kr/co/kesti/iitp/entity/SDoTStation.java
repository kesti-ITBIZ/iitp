package kr.co.kesti.iitp.entity;

import lombok.*;

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
public class SDoTStation implements Serializable {
    @Id
    @Column(name = "STN_ID", length = 30)
    private String stnId;

    @Column(name = "ADDR", length = 500, nullable = false)
    private String addr;

    @Column(name = "YEAR", length = 4, nullable = false)
    private String year;

    @Column(name = "HEIGHT", precision = 5, scale = 2)
    private Float height;

    @Column(name = "LO", precision = 14, scale = 10)
    private Float lo;

    @Column(name = "LA", precision = 14, scale = 10)
    private Float la;
}
