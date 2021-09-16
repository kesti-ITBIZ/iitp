package kr.co.kesti.iitp.service;

import kr.co.kesti.iitp.entity.Station;
import kr.co.kesti.iitp.repository.AirkoreaRepository;
import kr.co.kesti.iitp.repository.AirmapRepository;
import kr.co.kesti.iitp.repository.ObserverRepository;
import kr.co.kesti.iitp.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {
    private final AirkoreaRepository airkoreaRepository;
    private final AirmapRepository airmapRepository;
    private final ObserverRepository observerRepository;
    private final StationRepository stationRepository;

    public List<Station> stationsAll() {
        return this.stationRepository.findAll();
    }

    public List<Station> stations(final String category) {
        return this.stationRepository.findAllByCategory(category);
    }
}
