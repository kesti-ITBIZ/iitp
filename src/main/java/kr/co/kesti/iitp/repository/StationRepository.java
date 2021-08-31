package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findAllByCategory(final String category);
}
