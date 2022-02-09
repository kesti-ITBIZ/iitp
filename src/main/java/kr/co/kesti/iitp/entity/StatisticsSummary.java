package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_DNSTY_DATA_SUMMARY")
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "stcs_time", length = 8)
    private String statisticsTime;

    @Column(name = "stn_id")
    private String stnId;

    @Column(name = "stn_nm")
    private String stnNm;

    @Column(name = "avg_pm10", precision = 6, scale = 1)
    private Float avgPm10;

    @Column(name = "pm10_good_days", precision = 6, scale = 1)
    private Float pm10_GoodDays;

    @Column(name = "pm10_bad_days", precision = 6, scale = 1)
    private Float pm10_BadDays;

    @Column(name = "pm10_high_dnsty_days", precision = 6, scale = 1)
    private Float pm10_HighDnstyDays;

    @Column(name = "avg_pm25", precision = 6, scale = 1)
    private Float avgPm25;

    @Column(name = "pm25_good_days", precision = 6, scale = 1)
    private Float pm25_GoodDays;

    @Column(name = "pm25_bad_days", precision = 6, scale = 1)
    private Float pm25_BadDays;

    @Column(name = "pm25_high_dnsty_days", precision = 6, scale = 1)
    private Float pm25_HighDnstyDays;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regist_dt")
    @Builder.Default
    private Date registDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updt_dt")
    @Builder.Default
    private Date updatedDate = new Date();
}
