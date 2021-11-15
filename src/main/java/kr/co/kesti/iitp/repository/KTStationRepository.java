package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.KTStation;
import kr.co.kesti.iitp.entity.ObserverStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KTStationRepository extends JpaRepository<KTStation, String> {
    @Query(
            "select distinct " +
            "   k.la as latitude, " +
            "   k.lo as longitude, " +
            "   k.devNm as name, " +
            "   case " +
            "       when k.newaddr is null or k.newaddr = '' then k.addr " +
            "       else k.newaddr " +
            "   end as address " +
            "from KTStation k " +
            "where k.la is not null " +
            "and k.lo is not null")
    List<StationProjection> findAllBy();
}
