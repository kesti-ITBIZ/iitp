package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.SDoTStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SDoTStationRepository extends JpaRepository<SDoTStation, String> {

}
