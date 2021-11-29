package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QObserverData;
import kr.co.kesti.iitp.dsl.entity.QObserverStation;
import kr.co.kesti.iitp.entity.ObserverData;
import kr.co.kesti.iitp.vo.ResponseObserverDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ObserverDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public ObserverDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(ObserverData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseObserverDataVO> findAllDataByDatetime(
            final Date startDatetime,
            final Date endDatetime,
            final String dateType,
            final String stnNm) {
        QObserverData a = QObserverData.observerData;
        QObserverStation b = QObserverStation.observerStation;

        String format = "YYYY.MM.DD HH24:MI:SS";
//        switch (dateType) {
//        case "hour": format = "YYYY.MM.DD HH24"; break;
//        case "date": format = "YYYY.MM.DD"; break;
//        case "month": format = "YYYY.MM"; break;
//        case "year": format = "YYYY"; break;
//        }

        final StringTemplate datetime = Expressions.stringTemplate(String.format("to_char({0}, '%s')", format), a.observerDataKey.dataTime);

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseObserverDataVO.class,
                        datetime.as("datetime"),
                        b.stnNm.as("stnNm"),
                        a.temperature.as("temperature"),
                        a.humidity.as("humidity"),
                        a.pressure.as("pressure"),
                        a.pm25.as("pm25")))
                .from(a)
                .join(b)
                .on(a.observerDataKey.stnSerial.eq(b.stnSerial))
                .where(
                        a.observerDataKey.dataTime.between(startDatetime, endDatetime)
                        .and(b.stnNm.eq(stnNm)))
                .fetch();
    }

    public List<ResponseObserverDataVO> findAllDataByItem(
            final Date startDatetime,
            final Date endDatetime,
            final String stnNm) {
        QObserverData a = QObserverData.observerData;
        QObserverStation b = QObserverStation.observerStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseObserverDataVO.class,
                        b.stnNm.as("stnNm"),
                        a.temperature.as("temperature"),
                        a.humidity.as("humidity"),
                        a.pressure.as("pressure"),
                        a.pm25.as("pm25")))
                .from(a)
                .join(b)
                .on(a.observerDataKey.stnSerial.eq(b.stnSerial))
                .where(
                        a.observerDataKey.dataTime.between(startDatetime, endDatetime)
                        .and(b.stnNm.eq(stnNm)))
                .fetch();
    }
}
