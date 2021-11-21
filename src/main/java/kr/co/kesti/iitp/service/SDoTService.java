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

    public List<ResponseSDoTDataVO> getSDoTData(final RequestDataVO request) {
        return this.sDoTDataRepositoryDsl.findAllData(
                request.getStartDatetime(),
                request.getEndDatetime(),
                request.getStnNm(),
                request.getPm10(),
                request.getPm25());
    }
}
