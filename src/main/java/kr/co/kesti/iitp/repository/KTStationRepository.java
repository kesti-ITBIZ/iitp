package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.KTStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KTStationRepository extends JpaRepository<KTStation, String> {
    @Query(
            "select distinct " +
            "   'KT' as category, " +
            "   b.latitude as latitude, " +
            "   b.longitude as longitude, " +
            "   b.devNm as name, " +
            "   case " +
            "       when b.newAddress is null or b.newAddress = '' then b.address " +
            "       else b.newAddress " +
            "   end as address, " +
            "   function('round', avg(a.pm25)) as pm25 " +
            "from KTData a " +
            "join KTStation b " +
            "on a.ktDataKey.devId = b.devId " +
            "where a.ktDataKey.equipDate " +
            "   between :startDatetime " +
            "   and :endDatetime " +
            "and b.latitude is not null " +
            "and b.longitude is not null " +
            "group by " +
            "   b.latitude, " +
            "   b.longitude, " +
            "   b.devNm, " +
            "   case " +
            "       when b.newAddress is null or b.newAddress = '' then b.address " +
            "       else b.newAddress " +
            "   end")
    List<StationProjection> findAllBy(@Param("startDatetime") final Date startDatetime, @Param("endDatetime") final Date endDatetime);

    @Query(
            "select distinct " +
            "   'KT' as category, " +
            "   k.latitude as latitude, " +
            "   k.longitude as longitude, " +
            "   k.devNm as name, " +
            "   case " +
            "       when k.newAddress is null or k.newAddress = '' then k.address " +
            "       else k.newAddress " +
            "   end as address " +
            "from KTStation k " +
            "where k.latitude is not null " +
            "and k.longitude is not null " +
            "and ( " +
            "   (k.devNm is not null and k.devNm like concat('%', :keyword, '%')) " +
            "   or (k.devNm is not null and k.devNm like concat('%', :keyword, '%')) " +
            "   or (k.newAddress is not null and k.newAddress like concat('%', :keyword, '%')))")
    List<StationProjection> findAllByKeyword(@Param("keyword") final String keyword, final Pageable pageable);
}
