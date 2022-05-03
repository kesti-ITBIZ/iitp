package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.AirkoreaDataRepositoryDsl;
import kr.co.kesti.iitp.dsl.repository.AirkoreaPastDataRepositoryDsl;
import kr.co.kesti.iitp.repository.AirkoreaDataRepository;
import kr.co.kesti.iitp.repository.AirkoreaPastDataRepository;
import kr.co.kesti.iitp.repository.AirkoreaStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseAirkoreaDataVO;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirkoreaService implements GraphQLQueryResolver {
    private final AirkoreaDataRepositoryDsl airkoreaDataRepositoryDsl;
    private final AirkoreaPastDataRepositoryDsl airkoreaPastDataRepositoryDsl;
    private final AirkoreaStationRepository airkoreaStationRepository;
    private final AirkoreaDataRepository airkoreaDataRepository;
    private final AirkoreaPastDataRepository airkoreaPastDataRepository;

    public List<ResponseStationVO> getAirkoreaStationsByKeyword(final String keyword, final int page, final int size) {
        return this.airkoreaStationRepository.findAllByKeyword(keyword, PageRequest.of(page, size))
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseAirkoreaDataVO> getAirkoreaData(final RequestDataVO request) {

        String start = request.getStartDatetime();
        String end = request.getEndDatetime();

        List<ResponseAirkoreaDataVO> result = new ArrayList<>();

        if (Integer.parseInt(start.substring(0, 4)) <= 2021 && Integer.parseInt(end.substring(0, 4)) < 2022) {
            // 2018~2021 환경부 데이터
            result = this.airkoreaPastDataRepositoryDsl.findAllData(
                    start,
                    end,
                    request.getStnNm());
        } else if(Integer.parseInt(start.substring(0, 4)) <= 2021 && Integer.parseInt(end.substring(0, 4)) >= 2022) {
            // 2018~2022 환경부 데이터
            List<ResponseAirkoreaDataVO> result1 = new ArrayList<>();
            List<ResponseAirkoreaDataVO> result2 = new ArrayList<>();

            result1 = this.airkoreaPastDataRepositoryDsl.findAllData(
                    start,
                    "20211231235959",
                    request.getStnNm());

            result2 = this.airkoreaDataRepositoryDsl.findAllData(
                    "20220101000000",
                    end,
                    request.getStnNm());

            result.addAll(result1);
            result.addAll(result2);
        } else {
            // 2022년 이후 환경부 데이터
            result = this.airkoreaDataRepositoryDsl.findAllData(
                    start,
                    end,
                    request.getStnNm());
        }

        return result;
    }

    public List<String> getAirkoreaAvailableDatetimes() {
        final List<String> result = this.airkoreaDataRepository.findDistinctAllByOrderByDatetime();
        final List<String> pastResult = this.airkoreaPastDataRepository.findDistinctAllByOrderByDatetime();
        result.addAll(pastResult);
        Collections.sort(result);
        return result;
    }
}
