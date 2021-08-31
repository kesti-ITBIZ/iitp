package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.entity.Observer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObserverRepository extends JpaRepository<Observer, Integer> {
}
