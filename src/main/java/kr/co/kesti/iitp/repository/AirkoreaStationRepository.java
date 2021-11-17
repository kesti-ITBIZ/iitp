package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.AirkoreaStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaStationRepository extends JpaRepository<AirkoreaStation, String> {
    @Query(
            "select distinct " +
            "   a.latitude as latitude, " +
            "   a.longitude as longitude, " +
            "   a.stnNm as name, " +
            "   a.addr as address " +
            "from AirkoreaStation a " +
            "where a.latitude is not null " +
            "and a.longitude is not null")
    List<StationProjection> findAllBy();
}
