package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.SDoTQCDataKey;
import kr.co.kesti.iitp.entity.SDoTQCData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SDoTDataRepository extends JpaRepository<SDoTQCData, SDoTQCDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', s.sDoTQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from SDoTQCData s " +
            "where s.sDoTQCDataKey.time is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', function('to_timestamp', a.sDoTQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
            "    a.sDoTQCDataKey.stnId as stnId, " +
            "    b.stnId as stnNm, " +
            "    a.pm10_qc as pm10, " +
            "    a.pm25_qc as pm25 " +
            "from SDoTQCData a " +
            "join SDoTStation b " +
            "on a.sDoTQCDataKey.stnId = b.stnId " +
            "where a.sDoTQCDataKey.time " +
            "    between :startDatetime " +
            "    and :endDatetime " +
            "and (a.pm10_qc > 0 and a.pm25_qc > 0) " +
            "and ( " +
            "   a.sDoTQCDataKey.stnId = :stnId " +
            "   or b.stnId = :stnNm) " +
            "order by a.sDoTQCDataKey.time")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
