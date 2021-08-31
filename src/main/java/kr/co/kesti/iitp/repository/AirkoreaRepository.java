package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.Airkorea;
import kr.co.kesti.iitp.projection.AirkoreaInfoProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaRepository extends JpaRepository<Airkorea, Integer> {
    List<AirkoreaInfoProjection> findDistinctBy(final Pageable pageable);
}
