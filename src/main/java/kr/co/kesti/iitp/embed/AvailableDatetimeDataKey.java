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
public class AvailableDatetimeDataKey implements Serializable {
    @Column(name = "datetime", length = 30)
    private String datetime;

    @Column(name = "category", length = 20)
    private String category;
}
