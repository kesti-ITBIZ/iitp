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
public class Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String dataDate;
    private String dataTime;
    private Long serialNo;
    private String address;
    private Float latitude;
    private Float longitude;
    private Float pre;
    private Float tmp;
    private Float reh;
    private String qcPre;
    private String qcTmp;
    private String qcReh;
    private Float pm25;
    private Float pm25_R;
    private String qcPm25;
    private Float pty;
    private String ptyR;
}
