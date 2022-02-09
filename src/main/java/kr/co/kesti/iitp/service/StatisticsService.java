package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.repository.StatisticsSummaryRepository;
import kr.co.kesti.iitp.vo.ResponseStationVO;
import kr.co.kesti.iitp.vo.ResponseStatisticsDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatisticsService implements GraphQLQueryResolver {
    private final StatisticsSummaryRepository statisticsSummaryRepository;

    public List<ResponseStationVO> getStations(final String category, final String startDatetime, final String endDatetime) {
        return this.statisticsSummaryRepository.findAllByStatisticsTimeBetweenAndLatitudeIsNotNullAndLongitudeIsNotNull(category.toUpperCase(), startDatetime, endDatetime)
                .stream()
                .map(ResponseStationVO::from)
                .collect(Collectors.toList());
    }

    public List<ResponseStatisticsDataVO> getStatisticsData(final String category, final String startDatetime, final String endDatetime, final String stnNm) {
        return this.statisticsSummaryRepository.findAllByCategoryAndStatisticsTimeBetweenAndStnNm(category.toUpperCase(), startDatetime, endDatetime, stnNm)
                .stream()
                .map(projection -> ResponseStatisticsDataVO.builder()
                        .category(projection.getCategory())
                        .datetime(projection.getStatisticsTime())
                        .stnNm(projection.getStnNm())
                        .pm10(ResponseStatisticsDataVO.Value.builder()
                                .avg(projection.getAvgPm10())
                                .goodDays(projection.getPm10_GoodDays())
                                .badDays(projection.getPm10_BadDays())
                                .highDnstyDays(projection.getPm10_HighDnstyDays())
                                .build())
                        .pm25(ResponseStatisticsDataVO.Value.builder()
                                .avg(projection.getAvgPm25())
                                .goodDays(projection.getPm25_GoodDays())
                                .badDays(projection.getPm25_BadDays())
                                .highDnstyDays(projection.getPm25_HighDnstyDays())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }
}
