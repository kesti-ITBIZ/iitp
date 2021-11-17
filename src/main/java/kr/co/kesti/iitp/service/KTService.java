package kr.co.kesti.iitp.service;

import kr.co.kesti.iitp.projection.KTDataProjection;
import kr.co.kesti.iitp.projection.StationProjection;
import kr.co.kesti.iitp.repository.KTDataRepository;
import kr.co.kesti.iitp.repository.KTStationRepository;
import kr.co.kesti.iitp.vo.KTDataParamVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class KTService {
    private final KTDataRepository ktDataRepository;
    private final KTStationRepository ktStationRepository;

    public List<StationProjection> findAllStations() {
        return this.ktStationRepository.findAllBy();
    }

    public List<KTDataProjection> getData(final KTDataParamVO request) {
        return this.ktDataRepository.findAllData(request);
    }
}
