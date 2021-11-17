package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.SDoTDataKey;
import kr.co.kesti.iitp.entity.SDoTData;
import kr.co.kesti.iitp.projection.SDoTDataProjection;
import kr.co.kesti.iitp.vo.SDoTDataParamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SDoTDataRepository extends JpaRepository<SDoTData, SDoTDataKey> {
    @Query(
            "select " +
            "   a.sDoTDataKey.registTime as datetime, " +
            "   b.stnId as stnNm, " +
            "   a.temperature as temperature, " +
            "   a.relativeHumidity as humidity, " +
            "   case when a.windDirection = -2 then '' else a.windDirection end as windDirection, " +
            "   case when a.windSpeed = -2 then '' else a.windSpeed end as windSpeed, " +
            "   a.pm10 as pm10, " +
            "   a.pm25 as pm25 " +
            "from SDoTData a " +
            "join SDoTStation b " +
            "on a.sDoTDataKey.modelSr = b.stnId " +
            "where a.sDoTDataKey.registTime " +
            "   between :#{#params.getStartDatetime()} " +
            "   and :#{#params.getEndDatetime()} " +
            "and a.sDoTDataKey.modelSr = :#{#params.getStnNm()} " +
            "and a.sDoTDataKey.div = 1 " +
            "and a.pm10 " +
            "   between :#{#params.getPm10()[0]} " +
            "   and :#{#params.getPm10()[1]} " +
            "and a.pm25 " +
            "   between :#{#params.getPm25()[0]} " +
            "   and :#{#params.getPm25()[1]}")
    List<SDoTDataProjection> findAllData(final SDoTDataParamVO params);
}
