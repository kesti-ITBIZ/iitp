package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.AirkoreaQCDataKey;
import kr.co.kesti.iitp.entity.AirkoreaQCData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaDataRepository extends JpaRepository<AirkoreaQCData, AirkoreaQCDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from AirkoreaQCData a " +
            "where a.airkoreaQCDataKey.time is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
            "    a.airkoreaQCDataKey.stnId as stnId, " +
            "    b.stnNm as stnNm, " +
            "    a.pm10_qc as pm10, " +
            "    a.pm25_qc as pm25 " +
            "from AirkoreaQCData a " +
            "join AirkoreaStation b " +
            "on a.airkoreaQCDataKey.stnId = b.stnNm " +
            "where a.airkoreaQCDataKey.time " +
            "    between :startDatetime " +
            "    and :endDatetime " +
            "and (a.pm10_qc > 0 and a.pm25_qc > 0) " +
            "and ( " +
            "   a.airkoreaQCDataKey.stnId = :stnId " +
            "   or a.airkoreaQCDataKey.stnId = :stnNm) " +
            "order by a.airkoreaQCDataKey.time")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
