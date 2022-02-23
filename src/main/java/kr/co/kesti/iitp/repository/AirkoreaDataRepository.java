package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.AirkoreaDataKey;
import kr.co.kesti.iitp.entity.AirkoreaData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaDataRepository extends JpaRepository<AirkoreaData, AirkoreaDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from AirkoreaData a " +
            "where a.airkoreaDataKey.time is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
            "    a.airkoreaDataKey.stnNm as stnId, " +
            "    b.stnNm as stnNm, " +
            "    a.pm10 as pm10, " +
            "    a.pm25 as pm25 " +
            "from AirkoreaData a " +
            "join AirkoreaStation b " +
            "on a.airkoreaDataKey.stnNm = b.stnNm " +
            "where a.airkoreaDataKey.time " +
            "    between :startDatetime " +
            "    and :endDatetime " +
            "and ( " +
            "   a.airkoreaDataKey.stnNm = :stnId " +
            "   or a.airkoreaDataKey.stnNm = :stnNm) " +
            "order by a.airkoreaDataKey.time")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
