package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.SDoTDataKey;
import kr.co.kesti.iitp.entity.SDoTData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SDoTDataRepository extends JpaRepository<SDoTData, SDoTDataKey> {

}
