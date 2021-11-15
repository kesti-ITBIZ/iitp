package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.AirkoreaDataKey;
import kr.co.kesti.iitp.entity.AirkoreaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirkoreaDataRepository extends JpaRepository<AirkoreaData, AirkoreaDataKey> {

}
