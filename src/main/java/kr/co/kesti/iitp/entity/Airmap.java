package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airmap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String equipDate;
    private String spotDevSeq;
    private String spotDevId;
    private String spotDevNm;
    private String addr;
    private Float latitude;
    private Float longitude;
    private Float temprMsrAvgVal;
    private Float humdtMsrAvgVal;
    private Float pm10_MsrAvgVal;
    private Float pm25_MsrAvgVal;
}
