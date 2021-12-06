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
            final String stnNm) {
        QKTData a = QKTData.kTData;
        QKTStation b = QKTStation.kTStation;

        final StringTemplate datetime = Expressions.stringTemplate("to_char({0}, 'YYYY.MM.DD HH24:MI:SS')", a.ktDataKey.equipDate);

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseKTDataVO.class,
                        datetime.as("datetime"),
                        a.ktDataKey.devId.as("stnNm"),
                        a.temperature.as("temperature"),
                        a.humidity.as("humidity"),
                        a.pm10.as("pm10"),
                        a.pm25.as("pm25")))
                .from(a)
                .join(b)
                .on(a.ktDataKey.devId.eq(b.devId))
                .where(
                        a.ktDataKey.equipDate.between(startDatetime, endDatetime)
                        .and(b.devNm.eq(stnNm)))
                .fetch();
    }
}
