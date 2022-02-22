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

    @Query(value =
            "SELECT * FROM ( " +
            "    SELECT " +
            "        TO_CHAR(TO_TIMESTAMP(a.\"time\", 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS stdDatetime, " +
            "        a.stn_nm AS stdStnId, " +
            "        a.stn_nm AS stdStnNm, " +
            "        a.pm10 AS stdPm10, " +
            "        a.pm25 AS stdPm25 " +
            "    FROM \"TB_AIRKOREA_DATA_RAW\" a " +
            "    WHERE a.\"time\" " +
            "        BETWEEN :startDatetime " +
            "        AND :endDatetime " +
            "    AND a.stn_nm = :stdStnNm) c " +
            "JOIN ( " +
            "    SELECT " +
            "        TO_CHAR(TO_TIMESTAMP(a.regist_time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS compDatetime, " +
            "        a.model_sr AS compStnId, " +
            "        b.addr AS compStnNm, " +
            "        a.pm10 AS compPm10, " +
            "        a.pm25 AS compPm25 " +
            "    FROM \"TB_SDOT_DATA_RAW\" a " +
            "    JOIN \"TB_SDOT_STN_RAW\" b " +
            "    ON a.model_sr = b.stn_id " +
            "    WHERE a.regist_time " +
            "        BETWEEN :startDatetime " +
            "        AND :endDatetime " +
            "    AND a.model_sr = :compStnId) d " +
            "ON c.stdDatetime = d.compDatetime", nativeQuery = true)
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stdStnNm, final String compStnId);
}
