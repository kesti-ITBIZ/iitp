package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.AirkoreaStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirkoreaStationRepository extends JpaRepository<AirkoreaStation, String> {

}
