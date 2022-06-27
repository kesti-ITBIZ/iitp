package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.ObserverDataRepositoryDsl;
import kr.co.kesti.iitp.repository.ObserverDataRepository;
import kr.co.kesti.iitp.repository.ObserverStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseObserverDataVO;
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
public class ObserverService implements GraphQLQueryResolver {
    private final ObserverDataRepositoryDsl observerDataRepositoryDsl;
    private final ObserverStationRepository observerStationRepository;
    private final ObserverDataRepository observerDataRepository;

    public List<ResponseStationVO> getObserverStationsByKeyword(final String keyword, final int page, final int size) {
        return this.observerStationRepository.findAllByKeyword(keyword, PageRequest.of(page, size))
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseObserverDataVO> getObserverData(final RequestDataVO request) {
        return this.observerDataRepositoryDsl.findAllData(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getStnNm());
    }

    public List<String> getObserverAvailableDatetimes() {
        final List<String> result = this.observerDataRepository.findDistinctAllByOrderByDatetime();
        Collections.sort(result);
        return result;
    }
}
