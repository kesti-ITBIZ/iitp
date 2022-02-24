package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.SDoTStation;
import kr.co.kesti.iitp.projection.StationProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SDoTStationRepository extends JpaRepository<SDoTStation, String> {
    @Query(
            "select distinct " +
            "   'S-DoT' as category, " +
            "   s.latitude as latitude, " +
            "   s.longitude as longitude, " +
            "   s.stnId as name, " +
            "   s.stnId as stnId, " +
            "   s.address as address " +
            "from SDoTStation s " +
            "where s.latitude is not null " +
            "and s.longitude is not null " +
            "and ( " +
            "   (s.stnId is not null and s.stnId like concat('%', :keyword, '%')) " +
            "   or (s.address is not null and s.address like concat('%', :keyword, '%')))")
    List<StationProjection> findAllByKeyword(@Param("keyword") final String keyword, final Pageable pageable);
}
