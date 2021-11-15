package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.SDoTStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SDoTStationRepository extends JpaRepository<SDoTStation, String> {
    @Query(
            "select distinct " +
            "   s.la as latitude, " +
            "   s.lo as longitude, " +
            "   s.stnId as name, " +
            "   s.addr as address " +
            "from SDoTStation s " +
            "where s.la is not null " +
            "and s.lo is not null")
    List<StationProjection> findAllBy();
}
