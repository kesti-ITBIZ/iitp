package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.ObserverStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverStationRepository extends JpaRepository<ObserverStation, String> {
    @Query(
            "select distinct " +
            "   o.la as latitude, " +
            "   o.lo as longitude, " +
            "   o.stnNm as name, " +
            "   o.addr as address " +
            "from ObserverStation o " +
            "where o.la is not null " +
            "and o.lo is not null")
    List<StationProjection> findAllBy();
}
