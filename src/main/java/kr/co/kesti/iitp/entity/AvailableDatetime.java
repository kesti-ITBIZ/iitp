package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.AvailableDatetimeDataKey;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_AVAILABLE_DATETIME")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDatetime {

    @Type(type = "pg-uuid")
    @EmbeddedId
    private AvailableDatetimeDataKey availableDatetimeDataKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regist_dt")
    @Builder.Default
    private Date registDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updt_dt")
    @Builder.Default
    private Date updatedDate = new Date();
}
