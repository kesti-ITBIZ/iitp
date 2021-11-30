package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.SDoTDataRepositoryDsl;
import kr.co.kesti.iitp.repository.SDoTStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseSDoTDataVO;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SDoTService implements GraphQLQueryResolver {
    private final SDoTDataRepositoryDsl sDoTDataRepositoryDsl;
    private final SDoTStationRepository sDoTStationRepository;

    public List<ResponseStationVO> getSDoTStations() {
        return this.sDoTStationRepository.findAllBy()
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseStationVO> getSDoTStationsByKeyword(final String keyword) {
        return this.sDoTStationRepository.findAllByKeyword(keyword)
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseSDoTDataVO> getSDoTData(final RequestDataVO request) {
        return this.sDoTDataRepositoryDsl.findAllData(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getStnNm())
                .stream()
                .map(data -> ResponseSDoTDataVO.builder()
                        .datetime(data.getDatetime())
                        .stnNm(data.getStnNm())
                        .temperature(data.getTemperature())
                        .relativeHumidity(data.getRelativeHumidity())
                        .windDirection(data.getWindDirection() == -2.f ? null : data.getWindDirection())
                        .windSpeed(data.getWindSpeed() == -2.f ? null : data.getWindSpeed())
                        .pm10(data.getPm10())
                        .pm25(data.getPm25())
                        .build())
                .collect(Collectors.toList());
    }
}
