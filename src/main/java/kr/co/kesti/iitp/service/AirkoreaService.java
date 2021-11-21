package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.AirkoreaDataRepositoryDsl;
import kr.co.kesti.iitp.repository.AirkoreaStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseAirkoreaDataVO;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirkoreaService implements GraphQLQueryResolver {
    private final AirkoreaDataRepositoryDsl airkoreaDataRepositoryDsl;
    private final AirkoreaStationRepository airkoreaStationRepository;

    public List<ResponseStationVO> getAirkoreaStations() {
        return this.airkoreaStationRepository.findAllBy()
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseAirkoreaDataVO> getAirkoreaData(final RequestDataVO request) {
        return this.airkoreaDataRepositoryDsl.findAllData(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getStnNm(),
                request.getPm10(),
                request.getPm25());
    }
}
