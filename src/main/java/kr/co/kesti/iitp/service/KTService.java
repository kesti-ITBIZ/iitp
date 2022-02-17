package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.dsl.repository.KTDataRepositoryDsl;
import kr.co.kesti.iitp.repository.KTStationRepository;
import kr.co.kesti.iitp.repository.KtDataRepository;
import kr.co.kesti.iitp.vo.RequestDataVO;
import kr.co.kesti.iitp.vo.ResponseKTDataVO;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class KTService implements GraphQLQueryResolver {
    private final KTDataRepositoryDsl ktDataRepositoryDsl;
    private final KTStationRepository ktStationRepository;
    private final KtDataRepository ktDataRepository;

    public List<ResponseStationVO> getKtStationsByKeyword(final String keyword, final int page, final int size) {
        return this.ktStationRepository.findAllByKeyword(keyword, PageRequest.of(page, size))
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
                    request.getStnNm());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getKtAvailableDatetimes() {
        final List<String> result = this.ktDataRepository.findDistinctAllByOrderByDatetime();
        Collections.sort(result);
        return result;
    }
}
