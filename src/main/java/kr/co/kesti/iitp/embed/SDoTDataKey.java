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
    @Column(name = "REGIST_TIME", length = 30)
    private String registTime;

    @Column(name = "MODEL_SR", length = 30)
    private String modelSr;

    @Column(name = "DIV")
    private Long div;
}
