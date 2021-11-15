package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.ObserverStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObserverStationRepository extends JpaRepository<ObserverStation, String> {

}
