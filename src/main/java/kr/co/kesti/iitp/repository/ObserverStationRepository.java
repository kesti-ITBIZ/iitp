package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.ObserverStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ObserverStationRepository extends JpaRepository<ObserverStation, String> {
    @Query(
            "select distinct " +
            "   '옵저버' as category, " +
            "   b.latitude as latitude, " +
            "   b.longitude as longitude, " +
            "   b.stnNm as name, " +
            "   b.address as address, " +
            "   function('round', avg(a.pm25)) as pm25 " +
            "from ObserverData a " +
            "join ObserverStation b " +
            "on a.observerDataKey.stnSerial = b.stnSerial " +
            "where a.observerDataKey.dataTime " +
            "   between :startDatetime " +
            "   and :endDatetime " +
            "and b.latitude is not null " +
            "and b.longitude is not null " +
            "group by " +
            "   b.latitude, " +
            "   b.longitude, " +
            "   b.stnNm, " +
            "   b.address")
    List<StationProjection> findAllBy(@Param("startDatetime") final Date startDatetime, @Param("endDatetime") final Date endDatetime);

    @Query(
            "select distinct " +
            "   '옵저버' as category, " +
            "   o.latitude as latitude, " +
            "   o.longitude as longitude, " +
            "   o.stnNm as name, " +
            "   o.address as address " +
            "from ObserverStation o " +
            "where o.latitude is not null " +
            "and o.longitude is not null " +
            "and ( " +
            "   (o.stnNm is not null and o.stnNm like concat('%', :keyword, '%')) " +
            "   or (o.address is not null and o.address like concat('%', :keyword, '%')))")
    List<StationProjection> findAllByKeyword(@Param("keyword") final String keyword, final Pageable pageable);
}
