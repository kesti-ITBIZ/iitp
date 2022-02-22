package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.SDoTDataKey;
import kr.co.kesti.iitp.entity.SDoTData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SDoTDataRepository extends JpaRepository<SDoTData, SDoTDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', s.sDoTDataKey.registTime, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from SDoTData s " +
            "where s.sDoTDataKey.registTime is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', function('to_timestamp', a.sDoTDataKey.registTime, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
            "    a.sDoTDataKey.modelSr as stnId, " +
            "    b.address as stnNm, " +
            "    a.pm10 as pm10, " +
            "    a.pm25 as pm25 " +
            "from SDoTData a " +
            "join SDoTStation b " +
            "on a.sDoTDataKey.modelSr = b.stnId " +
            "where a.sDoTDataKey.registTime " +
            "    between :startDatetime " +
            "    and :endDatetime " +
            "and a.sDoTDataKey.modelSr = :stnId " +
            "order by a.sDoTDataKey.registTime")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId);
}
