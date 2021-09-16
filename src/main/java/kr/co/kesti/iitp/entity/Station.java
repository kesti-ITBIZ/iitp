package kr.co.kesti.iitp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 50)
    private String category;

    @Column(length = 100)
    private String name;

    private String address;
    private Float latitude;
    private Float longitude;
}
