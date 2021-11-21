package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QSDoTData;
import kr.co.kesti.iitp.dsl.entity.QSDoTStation;
import kr.co.kesti.iitp.entity.SDoTData;
import kr.co.kesti.iitp.vo.ResponseSDoTDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SDoTDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    private final CaseBuilder caseBuilder = new CaseBuilder();

    public SDoTDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(SDoTData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseSDoTDataVO> findAllData(
            final String startDatetime,
            final String endDatetime,
            final String stnNm,
            final List<Float> pm10,
            final List<Float> pm25) {
        QSDoTData a = QSDoTData.sDoTData;
        QSDoTStation b = QSDoTStation.sDoTStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseSDoTDataVO.class,
                        a.sDoTDataKey.registTime.as("datetime"),
                        b.stnId.as("stnNm"),
                        a.temperature.as("temperature"),
                        a.relativeHumidity.as("relativeHumidity"),
                        this.caseBuilder.when(a.windDirection.eq(-2.f)).then(Float.NaN).otherwise(a.windDirection).as("windDirection"),
                        this.caseBuilder.when(a.windSpeed.eq(-2.f)).then(Float.NaN).otherwise(a.windSpeed).as("windSpeed"),
                        a.pm10.as("pm10"),
                        a.pm25.as("pm25")))
                .from(a)
                .join(b)
                .on(a.sDoTDataKey.modelSr.eq(b.stnId))
                .where(
                        a.sDoTDataKey.registTime.between(startDatetime, endDatetime)
                        .and(a.sDoTDataKey.modelSr.eq(stnNm))
                        .and(a.sDoTDataKey.div.eq((short) 1))
                        .and(pm10.get(1) == null ?
                                a.pm25.goe(pm10.get(0)) :
                                a.pm25.between(pm10.get(0), pm10.get(1)))
                        .and(pm25.get(1) == null ?
                                a.pm25.goe(pm25.get(0)) :
                                a.pm25.between(pm25.get(0), pm25.get(1))))
                .fetch();
    }
}
