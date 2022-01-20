package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.embed.ObserverDataKey;
import kr.co.kesti.iitp.entity.ObserverData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObserverDataRepository extends JpaRepository<ObserverData, ObserverDataKey> {
    @Query(
            "select distinct function('to_char', o.observerDataKey.dataTime, 'YYYY.MM.DD HH24:00') as datetime " +
            "from ObserverData o " +
            "where o.observerDataKey.dataTime is not null")
    List<String> findDistinctAllByOrderByDatetime();
}
