package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.KTDataKey;
import kr.co.kesti.iitp.entity.KTData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KtDataRepository extends JpaRepository<KTData, KTDataKey> {
    @Query(
            "select distinct function('to_char', k.ktDataKey.equipDate, 'YYYY.MM.DD HH24:00') as datetime " +
            "from KTData k " +
            "where k.ktDataKey.equipDate is not null")
    List<String> findDistinctAllByOrderByDatetime();
}
