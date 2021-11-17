package kr.co.kesti.iitp.service;

import kr.co.kesti.iitp.dsl.repository.ObserverDataRepositoryDsl;
import kr.co.kesti.iitp.projection.ObserverDataProjection;
import kr.co.kesti.iitp.projection.StationProjection;
import kr.co.kesti.iitp.repository.ObserverStationRepository;
import kr.co.kesti.iitp.vo.ObserverDataParamVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ObserverService {
    private final ObserverDataRepositoryDsl observerDataRepositoryDsl;
    private final ObserverStationRepository observerStationRepository;

    public List<StationProjection> findAllStations() {
        return this.observerStationRepository.findAllBy();
    }

    public List<ObserverDataProjection> getData(final ObserverDataParamVO request) {
        return this.observerDataRepositoryDsl.findAllData(request);
    }
}
