package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.StatisticsSummary;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsSummaryRepository extends JpaRepository<StatisticsSummary, Long> {
    @Query(
            "select " +
            "   s.category as category, " +
            "   s.latitude as latitude, " +
            "   s.longitude as longitude, " +
            "   s.stnId as stnId, " +
            "   s.stnNm as name, " +
            "   s.address as address, " +
            "   function('round', avg(s.avgPm25)) as pm25 " +
            "from StatisticsSummary s " +
            "where s.category = :category " +
            "and s.statisticsTime " +
            "   between :startDatetime " +
            "   and :endDatetime " +
            "and s.latitude is not null " +
            "and s.longitude is not null " +
            "group by " +
            "   s.category, " +
            "   s.latitude, " +
            "   s.longitude, " +
            "   s.stnId, " +
            "   s.stnNm, " +
            "   s.address")
    List<StationProjection> findAllByStatisticsTimeBetweenAndLatitudeIsNotNullAndLongitudeIsNotNull(
            @Param("category") final String category,
            @Param("startDatetime") final String startDatetime,
            @Param("endDatetime") final String endDatetime);

    @Query(
            "select s from StatisticsSummary s " +
            "where s.category = :category " +
            "and ( " +
            "   s.statisticsTime " +
            "       between :startDatetime " +
            "       and :endDatetime " +
            "   or s.statisticsTime = substring(:startDatetime, 1, 4)) " +
            "and s.stnNm = :stnNm")
    List<StatisticsSummary> findAllByCategoryAndStatisticsTimeBetweenAndStnNm(
            @Param("category") final String category,
            @Param("startDatetime") final String startDatetime,
            @Param("endDatetime") final String endDatetime,
            @Param("stnNm") final String stnNm);
}
