package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airkorea {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 200)
    private String name;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date datetime;

    private Float so2;
    private Float co;
    private Float o3;
    private Float no2;
    private Float pm10;
    private Float khai;
    private Float pm25;
    private Integer so2v;
    private Integer cov;
    private Integer o3v;
    private Integer no2v;
    private Integer pm10v;
    private Integer khaiv;
    private Integer pm25v;

    @Column(length = 5)
    private String sido;
}
