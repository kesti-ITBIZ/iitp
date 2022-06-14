package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QObserverQCData;
import kr.co.kesti.iitp.dsl.entity.QObserverStation;
import kr.co.kesti.iitp.entity.ObserverData;
import kr.co.kesti.iitp.vo.ResponseObserverDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ObserverDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public ObserverDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(ObserverData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseObserverDataVO> findAllData(
            final String startDatetime,
            final String endDatetime,
            final String stnNm) {
        QObserverQCData a = QObserverQCData.observerQCData;
        QObserverStation b = QObserverStation.observerStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseObserverDataVO.class,
                        a.observerQCDataKey.time.as("datetime"),
                        b.stnNm.as("stnNm"),
                        a.tmp_qc.as("temperature"),
                        a.reh_qc.as("humidity"),
                        a.pm25_r_qc.as("pm25")))
                .from(a)
                .join(b)
                .on(a.observerQCDataKey.stnId.eq(b.stnSerial))
                .where(
                        a.observerQCDataKey.time.between(startDatetime, endDatetime)
                        .and(b.stnNm.eq(stnNm)))
                .fetch();
    }
}
