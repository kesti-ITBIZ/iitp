package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.KTDataKey;
import kr.co.kesti.iitp.entity.KTData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KtDataRepository extends JpaRepository<KTData, KTDataKey> {
    @Query(
            "select distinct function('to_char', k.ktDataKey.equipDate, 'YYYY.MM.DD HH24:00') as datetime " +
            "from KTData k " +
            "where k.ktDataKey.equipDate is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
            "    function('to_char', a.ktDataKey.equipDate, 'YYYYMMDDHH24') as datetime, " +
            "    a.ktDataKey.devId as dtnId, " +
            "    b.devNm as stnNm, " +
            "    a.pm10 as pm10, " +
            "    a.pm25 as pm25 " +
            "from KTData a " +
            "join KTStation b " +
            "on a.ktDataKey.devId = b.devId " +
            "where a.ktDataKey.equipDate " +
            "    between function('to_timestamp', concat(:startDatetime, ':00'), 'YYYY-MM-DD HH24:MI:SS') " +
            "    and function('to_timestamp', concat(:endDatetime, ':59'), 'YYYY-MM-DD HH24:MI:SS') " +
            "and a.ktDataKey.devId = :compStnId")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId);
}
