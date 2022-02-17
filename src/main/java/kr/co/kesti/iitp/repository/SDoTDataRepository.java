package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.SDoTDataKey;
import kr.co.kesti.iitp.entity.SDoTData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SDoTDataRepository extends JpaRepository<SDoTData, SDoTDataKey> {
    @Query(
            "select distinct function('to_char', function('to_timestamp', s.sDoTDataKey.registTime, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime " +
            "from SDoTData s " +
            "where s.sDoTDataKey.registTime is not null")
    List<String> findDistinctAllByOrderByDatetime();
}
