package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.KTStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KTStationRepository extends JpaRepository<KTStation, String> {
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
            "and k.longitude is not null")
    List<StationProjection> findAllBy();

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
