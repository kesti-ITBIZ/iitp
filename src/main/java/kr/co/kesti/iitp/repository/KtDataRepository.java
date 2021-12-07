package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.AirkoreaDataKey;
import kr.co.kesti.iitp.entity.AirkoreaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirkoreaDataRepository extends JpaRepository<AirkoreaData, AirkoreaDataKey> {
    @Query("select distinct function('to_char', function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYY.MM.DD HH24:00') as datetime from AirkoreaData a")
    List<String> findDistinctAllByOrderByDatetime();
}
