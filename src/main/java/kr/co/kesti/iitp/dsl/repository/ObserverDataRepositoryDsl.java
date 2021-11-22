package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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

    public List<ResponseObserverDataVO> findAllData(
            final Date startDatetime,
            final Date endDatetime,
            final String dateType,
            final String stnNm,
            final List<Float> pm25) {
        QObserverData a = QObserverData.observerData;
        QObserverStation b = QObserverStation.observerStation;

        String format = "";
        if ("hour".equals(dateType)) format = "YYYY.MM.DD HH24";
        else if ("date".equals(dateType)) format = "YYYY.MM.DD";
        else if ("month".equals(dateType)) format = "YYYY.MM";
        else if ("year".equals(dateType)) format = "YYYY";

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseObserverDataVO.class,
                        Expressions.stringTemplate("to_char({0}, '{1}')", a.observerDataKey.dataTime, format).as("datetime"),
                        b.stnNm.as("stnNm"),
                        a.temperature.avg().as("temperature"),
                        a.humidity.avg().as("humidity"),
                        a.pressure.avg().as("pressure"),
                        a.pm25.avg().as("pm25")))
                .from(a)
                .join(b)
                .on(a.observerDataKey.stnSerial.eq(b.stnSerial))
                .where(
                        a.observerDataKey.dataTime.between(startDatetime, endDatetime)
                        .and(b.stnNm.eq(stnNm))
                        .and(pm25.get(1) == null ?
                                a.pm25.goe(pm25.get(0)) :
                                a.pm25.between(pm25.get(0), pm25.get(1))))
                .groupBy(
                        Expressions.stringTemplate("to_char({0}, '{1}')", a.observerDataKey.dataTime, format),
                        b.stnNm)
                .fetch();
    }
}
