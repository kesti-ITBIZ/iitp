package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.KTStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KTStationRepository extends JpaRepository<KTStation, String> {
    @Query(
            "select distinct " +
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
}
