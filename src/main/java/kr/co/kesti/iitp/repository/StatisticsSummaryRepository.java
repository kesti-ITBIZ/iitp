package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.StatisticsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsSummaryRepository extends JpaRepository<StatisticsSummary, Long> {
    List<StatisticsSummary> findAllByCategoryAndStatisticsTimeBetweenAndStnNm(final String category, final String startDatetime, final String endDatetime, final String stnNm);
}
