package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.Airmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirmapRepository extends JpaRepository<Airmap, Integer> {
}
