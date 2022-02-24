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

    @Query(
            "select " +
            "    function('to_char', a.observerDataKey.dataTime, 'YYYYMMDDHH24') as datetime, " +
            "    a.observerDataKey.stnSerial as stnId, " +
            "    b.stnNm as stnNm, " +
            "    -999.f as pm10, " +
            "    a.pm25 as pm25 " +
            "from ObserverData a " +
            "join ObserverStation b " +
            "on a.observerDataKey.stnSerial = b.stnSerial " +
            "where a.observerDataKey.dataTime " +
            "    between function('to_timestamp', concat(:startDatetime, ':00'), 'YYYY-MM-DD HH24:MI:SS') " +
            "    and function('to_timestamp', concat(:endDatetime, ':59'), 'YYYY-MM-DD HH24:MI:SS') " +
            "and ( " +
            "   a.observerDataKey.stnSerial = :stnId " +
            "   or b.stnNm = :stnNm) " +
            "order by a.observerDataKey.dataTime")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
