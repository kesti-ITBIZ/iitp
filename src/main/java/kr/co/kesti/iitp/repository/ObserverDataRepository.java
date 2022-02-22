package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.ObserverDataKey;
import kr.co.kesti.iitp.entity.ObserverData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverDataRepository extends JpaRepository<ObserverData, ObserverDataKey> {
    @Query(
            "select distinct function('to_char', o.observerDataKey.dataTime, 'YYYY.MM.DD HH24:00') as datetime " +
            "from ObserverData o " +
            "where o.observerDataKey.dataTime is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(value =
            "SELECT * FROM ( " +
            "    SELECT " +
            "        TO_CHAR(TO_TIMESTAMP(a.\"time\", 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS std_datetime, " +
            "        a.stn_nm AS std_stn_id, " +
            "        a.stn_nm AS std_stn_nm, " +
            "        a.pm10 AS std_pm10, " +
            "        a.pm25 AS std_pm25 " +
            "    FROM \"TB_AIRKOREA_DATA_RAW\" a " +
            "    WHERE a.\"time\" " +
            "        BETWEEN :startDatetime " +
            "        AND :endDatetime " +
            "    AND a.stn_nm = :stdStnNm) c " +
            "JOIN ( " +
            "    SELECT " +
            "        TO_CHAR(a.data_time, 'YYYYMMDDHH24') AS comp_datetime, " +
            "        a.stn_serial AS comp_stn_id, " +
            "        b.stn_nm AS comp_stn_nm, " +
            "        NULL AS comp_pm10, " +
            "        a.pm25 AS comp_pm25 " +
            "    FROM \"TB_OBSERVER_DATA_RAW\" a " +
            "    JOIN \"TB_OBSERVER_STN_RAW\" b " +
            "    ON a.stn_serial = b.stn_serial " +
            "    WHERE a.data_time " +
            "        BETWEEN TO_TIMESTAMP(:startDatetime || ':00', 'YYYY-MM-DD HH24:MI:SS') " +
            "        AND TO_TIMESTAMP(:endDatetime || ':59', 'YYYY-MM-DD HH24:MI:SS') " +
            "    AND a.stn_serial = :compStnId) d " +
            "ON c.std_datetime = d.comp_datetime", nativeQuery = true)
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stdStnNm, final String compStnId);
}
