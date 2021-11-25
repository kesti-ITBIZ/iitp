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

    public List<ResponseSDoTDataVO> getSDoTDataByDatetime(final RequestDataVO request) {
        return this.sDoTDataRepositoryDsl.findAllDataByDatetime(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getDateType(),
                request.getStnNm(),
                request.getPm10(),
                request.getPm25())
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

    public List<ResponseSDoTDataVO> getSDoTDataByItem(final RequestDataVO request) {
        return this.sDoTDataRepositoryDsl.findAllDataByItem(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getStnNm(),
                request.getPm10(),
                request.getPm25())
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
