package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
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
            final String dateType,
            final String stnNm,
            final List<Float> pm10,
            final List<Float> pm25) {
        QSDoTData a = QSDoTData.sDoTData;
        QSDoTStation b = QSDoTStation.sDoTStation;

        String format = "";
        if ("hour".equals(dateType)) format = "YYYY.MM.DD HH24";
        else if ("date".equals(dateType)) format = "YYYY.MM.DD";
        else if ("month".equals(dateType)) format = "YYYY.MM";
        else if ("year".equals(dateType)) format = "YYYY";

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseSDoTDataVO.class,
                        Expressions.stringTemplate("to_char(to_timestamp({0}, 'YYYY-MM-DD HH24:MI'), '{1}')", a.sDoTDataKey.registTime, format).as("datetime"),
                        b.stnId.as("stnNm"),
                        a.temperature.avg().as("temperature"),
                        a.relativeHumidity.avg().as("relativeHumidity"),
                        a.windDirection.avg().as("windDirection"),
                        a.windSpeed.avg().as("windSpeed"),
                        a.pm10.avg().as("pm10"),
                        a.pm25.avg().as("pm25")))
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
                .groupBy(
                        Expressions.stringTemplate("to_char(to_timestamp({0}, 'YYYY-MM-DD HH24:MI'), '{1}')", a.sDoTDataKey.registTime, format),
                        b.stnId)
                .fetch();
    }
}
