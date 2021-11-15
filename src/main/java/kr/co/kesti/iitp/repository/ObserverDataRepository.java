package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.ObserverDataKey;
import kr.co.kesti.iitp.entity.ObserverData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObserverDataRepository extends JpaRepository<ObserverData, ObserverDataKey> {

}
