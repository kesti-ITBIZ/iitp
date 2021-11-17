package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.ObserverDataKey;
import kr.co.kesti.iitp.entity.ObserverData;
import kr.co.kesti.iitp.projection.ObserverDataProjection;
import kr.co.kesti.iitp.vo.ObserverDataParamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverDataRepository extends JpaRepository<ObserverData, ObserverDataKey> {
    @Query(
            "select " +
            "   concat(" +
            "       function('to_char', a.observerDataKey.dataDate, 'YYYYMMDD'), " +
            "       function('to_char', a.observerDataKey.dataTime, 'HH24MISS')) as datetime, " +
            "   b.stnNm as stnNm, " +
            "   a.temperature as temperature, " +
            "   a.humidity as humidity, " +
            "   a.pressure as pressure, " +
            "   a.pm25 as pm25 " +
            "from ObserverData a " +
            "join ObserverStation b " +
            "on a.observerDataKey.stnSerial = b.stnSerial " +
            "where a.observerDataKey.dataDate " +
            "   between :#{#params.getStartDatetime()} " +
            "   and :#{#params.getEndDatetime()} " +
            "and a.observerDataKey.dataTime " +
            "   between :#{#params.getStartDatetime()} " +
            "   and :#{#params.getEndDatetime()} " +
            "and b.stnNm = :#{#params.getStnNm()} " +
            "and a.pm25 " +
            "   between :#{#params.getPm25()[0]} " +
            "   and :#{#params.getPm25()[1]}")
    List<ObserverDataProjection> findAllData(final ObserverDataParamVO params);
}
