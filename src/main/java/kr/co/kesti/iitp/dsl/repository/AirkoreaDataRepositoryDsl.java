package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QAirkoreaData;
import kr.co.kesti.iitp.dsl.entity.QAirkoreaStation;
import kr.co.kesti.iitp.entity.AirkoreaData;
import kr.co.kesti.iitp.vo.ResponseAirkoreaDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AirkoreaDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public AirkoreaDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(AirkoreaData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseAirkoreaDataVO> findAllData(
            final String startDatetime,
            final String endDatetime,
            final String dateType,
            final String stnNm,
            final List<Float> pm10,
            final List<Float> pm25) {
        QAirkoreaData a = QAirkoreaData.airkoreaData;
        QAirkoreaStation b = QAirkoreaStation.airkoreaStation;

        String format = "";
        if (dateType.equals("hour")) format = "YYYY.MM.DD HH24";
        else if (dateType.equals("date")) format = "YYYY.MM.DD";
        else if (dateType.equals("month")) format = "YYYY.MM";
        else if (dateType.equals("year")) format = "YYYY";

        final StringTemplate datetime = Expressions.stringTemplate(String.format("to_char(to_timestamp({0}, 'YYYY-MM-DD HH24:MI'), '%s')", format), a.airkoreaDataKey.time);

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseAirkoreaDataVO.class,
                        datetime.as("datetime"),
                        a.airkoreaDataKey.stnNm.as("stnNm"),
                        a.so2.avg().floatValue().as("so2"),
                        a.co.avg().floatValue().as("co"),
                        a.o3.avg().floatValue().as("o3"),
                        a.no2.avg().floatValue().as("no2"),
                        a.pm10.avg().floatValue().as("pm10"),
                        a.pm25.avg().floatValue().as("pm25")))
                .from(a)
                .join(b)
                .on(a.airkoreaDataKey.stnNm.eq(b.stnNm))
                .where(
                        a.airkoreaDataKey.time.between(startDatetime, endDatetime)
                        .and(a.airkoreaDataKey.stnNm.eq(stnNm))
                        .and(pm10.get(1) == null ?
                                a.pm10.goe(pm10.get(0)) :
                                a.pm10.between(pm10.get(0), pm10.get(1)))
                        .and(pm25.get(1) == null ?
                                a.pm25.goe(pm25.get(0)) :
                                a.pm25.between(pm25.get(0), pm25.get(1))))
                .groupBy(datetime, a.airkoreaDataKey.stnNm)
                .fetch();
    }
}
