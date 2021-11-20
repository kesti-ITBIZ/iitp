package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QKTData;
import kr.co.kesti.iitp.dsl.entity.QKTStation;
import kr.co.kesti.iitp.dsl.entity.QObserverData;
import kr.co.kesti.iitp.dsl.entity.QObserverStation;
import kr.co.kesti.iitp.entity.KTData;
import kr.co.kesti.iitp.entity.ObserverData;
import kr.co.kesti.iitp.projection.KTDataProjection;
import kr.co.kesti.iitp.projection.ObserverDataProjection;
import kr.co.kesti.iitp.vo.KTDataParamVO;
import kr.co.kesti.iitp.vo.ObserverDataParamVO;
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

    public List<ObserverDataProjection> findAllData(final ObserverDataParamVO params) {
        QObserverData a = QObserverData.observerData;
        QObserverStation b = QObserverStation.observerStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ObserverDataProjection.class,
                        Expressions.stringTemplate("to_date({0}, 'YYYYMMDD')", a.observerDataKey.dataTime).as("datetime"),
                        b.stnNm.as("stnNm"),
                        a.temperature.as("temperature"),
                        a.humidity.as("humidity"),
                        a.pressure.as("pressure"),
                        a.pm25.as("pm25")))
                .from(a)
                .join(b)
                .on(a.observerDataKey.stnSerial.eq(b.stnSerial))
                .where(
                        a.observerDataKey.dataTime.between(params.getStartDatetime(), params.getEndDatetime())
                        .and(b.stnNm.eq(params.getStnNm()))
                        .and(params.getPm25().get(1) == null ?
                                a.pm25.goe(params.getPm25().get(0)) :
                                a.pm25.between(params.getPm25().get(0), params.getPm25().get(1))))
                .fetch();
    }
}
