package kr.co.kesti.iitp.embed;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SDoTDataKey implements Serializable {
    @Column(name = "regist_time", length = 30)
    private String registTime;

    @Column(name = "model_sr", length = 30)
    private String modelSr;

    @Column(name = "div")
    private Short div;
}
