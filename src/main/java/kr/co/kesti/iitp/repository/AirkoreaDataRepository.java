package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.AirkoreaDataKey;
import kr.co.kesti.iitp.entity.AirkoreaData;
import kr.co.kesti.iitp.projection.AirkoreaDataProjection;
import kr.co.kesti.iitp.vo.AirkoreaDataParamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaDataRepository extends JpaRepository<AirkoreaData, AirkoreaDataKey> {
    @Query(
            "select " +
            "   a.airkoreaDataKey.time as datetime, " +
            "   a.airkoreaDataKey.stnNm as stnNm, " +
            "   a.so2 as so2, " +
            "   a.co as co, " +
            "   a.o3 as o3, " +
            "   a.no2 as no2, " +
            "   a.pm10 as pm10, " +
            "   a.pm25 as pm25 " +
            "from AirkoreaData a " +
            "join AirkoreaStation b " +
            "on a.airkoreaDataKey.stnNm = b.stnNm " +
            "where a.airkoreaDataKey.time " +
            "   between :#{#params.getStartDatetime()} " +
            "   and :#{#params.getEndDatetime()} " +
            "and a.airkoreaDataKey.stnNm = :#{#params.getStnNm()} " +
            "and a.pm10 " +
            "   between :#{#params.getPm10()[0]} " +
            "   and :#{#params.getPm10()[1]} " +
            "and a.pm25 " +
            "   between :#{#params.getPm25()[0]} " +
            "   and :#{#params.getPm25()[1]}")
    List<AirkoreaDataProjection> findAllData(final AirkoreaDataParamVO params);
}
