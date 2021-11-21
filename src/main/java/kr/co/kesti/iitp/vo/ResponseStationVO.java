package kr.co.kesti.iitp.vo;

import kr.co.kesti.iitp.projection.StationProjection;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStationVO {
    private Float latitude;
    private Float longitude;
    private String name;
    private String address;

    public static ResponseStationVO from(StationProjection projection) {
        return ResponseStationVO.builder()
                .latitude(projection.getLatitude())
                .longitude(projection.getLongitude())
                .name(projection.getName())
                .address(projection.getAddress())
                .build();
    }
}
