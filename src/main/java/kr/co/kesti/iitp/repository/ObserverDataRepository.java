package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.ObserverQCDataKey;
import kr.co.kesti.iitp.entity.ObserverQCData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverDataRepository extends JpaRepository<ObserverQCData, ObserverQCDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', o.observerQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from ObserverQCData o " +
            "where o.observerQCDataKey.time is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', function('to_timestamp', a.observerQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime, " +
            "    a.observerQCDataKey.stnId as stnId, " +
            "    b.stnNm as stnNm, " +
            "    -999.f as pm10, " +
            "    a.pm25_r_qc as pm25 " +
            "from ObserverQCData a " +
            "join ObserverStation b " +
            "on a.observerQCDataKey.stnId = b.stnSerial " +
            "where a.observerQCDataKey.time " +
            "    between :startDatetime " +
            "    and :endDatetime " +
            "and a.pm25_r_qc > 0 " +
            "and ( " +
            "   a.observerQCDataKey.stnId = :stnId " +
            "   or b.stnNm = :stnNm) " +
            "order by a.observerQCDataKey.time")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
