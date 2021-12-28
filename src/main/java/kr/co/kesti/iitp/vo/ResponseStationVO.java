package kr.co.kesti.iitp.vo;

import kr.co.kesti.iitp.projection.StationProjection;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStationVO {
    private String category;
    private Float latitude;
    private Float longitude;
    private String name;
    private String address;
    private Float pm25;

    public static ResponseStationVO from(StationProjection projection) {
        return ResponseStationVO.builder()
                .category(projection.getCategory())
                .latitude(projection.getLatitude())
                .longitude(projection.getLongitude())
                .name(projection.getName())
                .address(projection.getAddress())
                .pm25(projection.getPm25())
                .build();
    }
}
