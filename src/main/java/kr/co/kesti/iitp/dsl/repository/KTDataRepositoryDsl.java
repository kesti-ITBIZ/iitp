package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QKTData;
import kr.co.kesti.iitp.dsl.entity.QKTStation;
import kr.co.kesti.iitp.entity.KTData;
import kr.co.kesti.iitp.vo.ResponseKTDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class KTDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public KTDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(KTData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseKTDataVO> findAllData(
            final Date startDatetime,
            final Date endDatetime,
            final String dateType,
            final String stnNm,
            final List<Float> pm10,
            final List<Float> pm25) {
        QKTData a = QKTData.kTData;
        QKTStation b = QKTStation.kTStation;

        String format = "";
        if (dateType.equals("hour")) format = "YYYY.MM.DD HH24";
        else if (dateType.equals("date")) format = "YYYY.MM.DD";
        else if (dateType.equals("month")) format = "YYYY.MM";
        else if (dateType.equals("year")) format = "YYYY";

        final StringTemplate datetime = Expressions.stringTemplate(String.format("to_char({0}, '%s')", format), a.ktDataKey.equipDate);

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseKTDataVO.class,
                        datetime.as("datetime"),
                        a.ktDataKey.devId.as("stnNm"),
                        a.temperature.avg().floatValue().as("temperature"),
                        a.humidity.avg().floatValue().as("humidity"),
                        a.pm10.avg().floatValue().as("pm10"),
                        a.pm25.avg().floatValue().as("pm25")))
                .from(a)
                .join(b)
                .on(a.ktDataKey.devId.eq(b.devId))
                .where(
                        a.ktDataKey.equipDate.between(startDatetime, endDatetime)
                        .and(a.ktDataKey.devId.eq(stnNm))
                        .and(pm10.get(1) == null ?
                                a.pm10.goe(pm10.get(0)) :
                                a.pm10.between(pm10.get(0), pm10.get(1)))
                        .and(pm25.get(1) == null ?
                                a.pm25.goe(pm25.get(0)) :
                                a.pm25.between(pm25.get(0), pm25.get(1))))
                .groupBy(datetime, a.ktDataKey.devId)
                .fetch();
    }
}
