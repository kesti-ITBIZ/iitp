package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.KTQCDataKey;
import kr.co.kesti.iitp.entity.KTQCData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KtDataRepository extends JpaRepository<KTQCData, KTQCDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', k.ktQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from KTQCData k " +
            "where k.ktQCDataKey.time is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', function('to_timestamp', a.ktQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime, " +
            "    a.ktQCDataKey.stnId as stnId, " +
            "    b.devNm as stnNm, " +
            "    a.pm10_qc as pm10, " +
            "    a.pm25_qc as pm25 " +
            "from KTQCData a " +
            "join KTStation b " +
            "on a.ktQCDataKey.stnId = b.devId " +
            "where a.ktQCDataKey.time " +
            "    between :startDatetime " +
            "    and :endDatetime " +
            "and (a.pm10_qc > -900 and a.pm25_qc > -900) " +
            "and ( " +
            "   a.ktQCDataKey.stnId = :stnId " +
            "   or b.devNm = :stnNm) " +
            "order by a.ktQCDataKey.time")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
