package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.AirkoreaPastDataKey;
import kr.co.kesti.iitp.entity.AirkoreaPastData;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaPastDataRepository extends JpaRepository<AirkoreaPastData, AirkoreaPastDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from AirkoreaPastData a " +
            "where a.airkoreaPastDataKey.dataDatetime is not null")
    List<String> findDistinctAllByOrderByDatetime();

    @Query(
            "select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaPastDataKey.stnNm as stnId, " +
                    "    b.stnNm as stnNm, " +
                    "    a.pm10 as pm10, " +
                    "    a.pm25 as pm25 " +
                    "from AirkoreaPastData a " +
                    "join AirkoreaStation b " +
                    "on a.airkoreaPastDataKey.stnNm = b.stnNm " +
                    "where a.airkoreaPastDataKey.dataDatetime " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and (a.pm10 <> -999 and a.pm25 <> -999) " +
                    "and ( " +
                    "   a.airkoreaPastDataKey.stnNm = :stnId " +
                    "   or a.airkoreaPastDataKey.stnNm = :stnNm) " +
                    "order by a.airkoreaPastDataKey.dataDatetime")
    List<ComparativeDataProjection> findAllComparativeData(final String startDatetime, final String endDatetime, final String stnId, final String stnNm);
}
