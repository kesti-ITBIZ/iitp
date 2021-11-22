package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kesti.iitp.dsl.repository.ObserverDataRepositoryDsl;
import kr.co.kesti.iitp.repository.ObserverStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseObserverDataVO;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ObserverService implements GraphQLQueryResolver {
    private final ObserverDataRepositoryDsl observerDataRepositoryDsl;
    private final ObserverStationRepository observerStationRepository;

    public List<ResponseStationVO> getObserverStations() {
        return this.observerStationRepository.findAllBy()
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    private final ObjectMapper jacksonObjectMapper;

    public List<ResponseObserverDataVO> getObserverData(final RequestDataVO request) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return this.observerDataRepositoryDsl.findAllData(
                    dateFormat.parse(request.getStartDatetime()),
                    dateFormat.parse(request.getEndDatetime()),
                    request.getDateType(),
                    request.getStnNm(),
                    request.getPm25());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
