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
