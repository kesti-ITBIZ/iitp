package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.AirkoreaStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaStationRepository extends JpaRepository<AirkoreaStation, String> {
    @Query(
            "select distinct " +
            "   '환경부' as category, " +
            "   b.latitude as latitude, " +
            "   b.longitude as longitude, " +
            "   b.stnNm as name, " +
            "   b.addr as address, " +
            "   function('round', avg(a.pm25)) as pm25 " +
            "from AirkoreaData a " +
            "join AirkoreaStation b " +
            "on a.airkoreaDataKey.stnNm = b.stnNm " +
            "where a.airkoreaDataKey.time " +
            "   between :startDatetime " +
            "   and :endDatetime " +
            "and b.latitude is not null " +
            "and b.longitude is not null " +
            "group by " +
            "   b.latitude, " +
            "   b.longitude, " +
            "   b.stnNm, " +
            "   b.addr")
    List<StationProjection> findAllBy(@Param("startDatetime") final String startDatetime, @Param("endDatetime") final String endDatetime);

    @Query(
            "select distinct " +
            "   '환경부' as category, " +
            "   a.latitude as latitude, " +
            "   a.longitude as longitude, " +
            "   a.stnNm as name, " +
            "   a.addr as address " +
            "from AirkoreaStation a " +
            "where a.latitude is not null " +
            "and a.longitude is not null " +
            "and ( " +
            "   (a.stnNm is not null and a.stnNm like concat('%', :keyword, '%')) " +
            "   or (a.addr is not null and a.addr like concat('%', :keyword, '%')))")
    List<StationProjection> findAllByKeyword(@Param("keyword") final String keyword, final Pageable pageable);
}
