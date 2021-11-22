package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.KTDataRepositoryDsl;
import kr.co.kesti.iitp.repository.KTStationRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseKTDataVO;
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
public class KTService implements GraphQLQueryResolver {
    private final KTDataRepositoryDsl ktDataRepositoryDsl;
    private final KTStationRepository ktStationRepository;

    public List<ResponseStationVO> getKtStations() {
        return this.ktStationRepository.findAllBy()
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseKTDataVO> getKtData(final RequestDataVO request) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return this.ktDataRepositoryDsl.findAllData(
                    dateFormat.parse(request.getStartDatetime()),
                    dateFormat.parse(request.getEndDatetime()),
                    request.getDateType(),
                    request.getStnNm(),
                    request.getPm10(),
                    request.getPm25());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
