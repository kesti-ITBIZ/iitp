
package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.KTDataKey;
import kr.co.kesti.iitp.entity.KTData;
import kr.co.kesti.iitp.projection.KTDataProjection;
import kr.co.kesti.iitp.vo.KTDataParamVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KTDataRepository extends JpaRepository<KTData, KTDataKey> {
    @Query(
            "select " +
            "   function('to_char', a.tbKtDataRawKey.equipDate, 'YYYYMMDDHH24MISS') as datetime, " +
            "   a.tbKtDataRawKey.devId as stnNm, " +
            "   a.temperature as temperature, " +
            "   a.humidity as humidity, " +
            "   a.pm10 as pm10, " +
            "   a.pm25 as pm25 " +
            "from KTData a " +
            "join KTStation b " +
            "on a.tbKtDataRawKey.devId = b.devId " +
            "where a.tbKtDataRawKey.equipDate " +
            "   between :#{#params.getStartDatetime()} " +
            "   and :#{#params.getEndDatetime()} " +
            "and a.tbKtDataRawKey.devId = :#{#params.getStnNm()} " +
            "and a.pm10 " +
            "   between :#{#params.getPm10()[0]} " +
            "   and :#{#params.getPm10()[1]} " +
            "and a.pm25 " +
            "   between :#{#params.getPm25()[0]} " +
            "   and :#{#params.getPm25()[1]}")
    List<KTDataProjection> findAllData(final KTDataParamVO params);
}
