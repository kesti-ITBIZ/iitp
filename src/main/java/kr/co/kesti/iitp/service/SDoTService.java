package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.SDoTDataRepositoryDsl;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import kr.co.kesti.iitp.repository.SDoTDataRepository;
import kr.co.kesti.iitp.repository.SDoTStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseSDoTDataVO;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SDoTService implements GraphQLQueryResolver {
    private final SDoTDataRepositoryDsl sDoTDataRepositoryDsl;
    private final SDoTStationRepository sDoTStationRepository;
    private final SDoTDataRepository sDoTDataRepository;

    public List<ResponseStationVO> getSDoTStationsByKeyword(final String keyword, final int page, final int size) {
        return this.sDoTStationRepository.findAllByKeyword(keyword, PageRequest.of(page, size))
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
                        .pm10(data.getPm10())
                        .pm25(data.getPm25())
                        .build())
                .collect(Collectors.toList());
    }

    public List<String> getSDoTAvailableDatetimes() {
        final List<String> result = this.sDoTDataRepository.findDistinctAllByOrderByDatetime();
        Collections.sort(result);
        return result;
    }
}
