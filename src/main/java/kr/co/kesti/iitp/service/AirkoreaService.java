package kr.co.kesti.iitp.service;

import kr.co.kesti.iitp.projection.StationProjection;
import kr.co.kesti.iitp.repository.AirkoreaStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirkoreaService {
    private final AirkoreaStationRepository airkoreaStationRepository;

    public List<StationProjection> findAllStations() {
        return this.airkoreaStationRepository.findAllBy();
    }
}
