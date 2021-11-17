package kr.co.kesti.iitp.entity;

import kr.co.kesti.iitp.embed.KTDataKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_KT_DATA_RAW")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KTData {
    @EmbeddedId
    private KTDataKey ktDataKey;

    @Column(name = "temp", precision = 7, scale = 2)
    private Float temperature;

    @Column(name = "hum", precision = 7, scale = 2)
    private Float humidity;

    @Column(name = "pm10", precision = 7, scale = 2)
    private Float pm10;

    @Column(name = "pm25", precision = 7, scale = 2)
    private Float pm25;
}
