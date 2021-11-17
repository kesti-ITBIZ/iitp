
package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.KTDataKey;
import kr.co.kesti.iitp.entity.KTData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KTDataRepository extends JpaRepository<KTData, KTDataKey> {

}
