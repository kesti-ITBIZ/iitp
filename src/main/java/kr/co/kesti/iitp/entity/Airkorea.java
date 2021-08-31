package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private Integer stationCode;
    private String station;
    private Date time;
    private String mang;
    private String region;
    private String address;
    private Float latitude;
    private Float longitude;
    private Float so2;
    private Float co;
    private Float o3;
    private Float no2;
    private Float pm10;
    private Float pm25;
}
