package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.AirkoreaDataRepositoryDsl;
import kr.co.kesti.iitp.repository.AirkoreaDataRepository;
import kr.co.kesti.iitp.repository.AirkoreaStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseAirkoreaDataVO;
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
public class AirkoreaService implements GraphQLQueryResolver {
    private final AirkoreaDataRepositoryDsl airkoreaDataRepositoryDsl;
    private final AirkoreaStationRepository airkoreaStationRepository;
    private final AirkoreaDataRepository airkoreaDataRepository;

    public List<ResponseStationVO> getAirkoreaStations(final String startDatetime, final String endDatetime) {
        return this.airkoreaStationRepository.findAllBy(startDatetime, endDatetime)
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseStationVO> getAirkoreaStationsByKeyword(final String keyword, final int page, final int size) {
        return this.airkoreaStationRepository.findAllByKeyword(keyword, PageRequest.of(page, size))
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseAirkoreaDataVO> getAirkoreaData(final RequestDataVO request) {
        return this.airkoreaDataRepositoryDsl.findAllData(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getStnNm());
    }

    public List<String> getAirkoreaAvailableDatetimes() {
        final List<String> result = this.airkoreaDataRepository.findDistinctAllByOrderByDatetime();
        Collections.sort(result);
        return result;
    }
}
