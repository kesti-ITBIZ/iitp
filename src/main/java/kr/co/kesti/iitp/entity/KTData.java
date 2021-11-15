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
    private KTDataKey tbKtDataRawKey;

    @Column(name = "TEMP", precision = 7, scale = 2)
    private Float temp;

    @Column(name = "HUM", precision = 7, scale = 2)
    private Float hum;

    @Column(name = "PM10", precision = 7, scale = 2)
    private Float pm10;

    @Column(name = "PM25", precision = 7, scale = 2)
    private Float pm25;
}
