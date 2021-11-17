package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QKTData;
import kr.co.kesti.iitp.dsl.entity.QKTStation;
import kr.co.kesti.iitp.entity.KTData;
import kr.co.kesti.iitp.projection.KTDataProjection;
import kr.co.kesti.iitp.vo.KTDataParamVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KTDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public KTDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(KTData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<KTDataProjection> findAllData(final KTDataParamVO params) {
        QKTData a = QKTData.kTData;
        QKTStation b = QKTStation.kTStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(KTDataProjection.class,
                        Expressions.stringTemplate("to_date({0}, 'YYYYMMDDHH24MISS')", a.ktDataKey.equipDate).as("datetime"),
                        a.ktDataKey.devId.as("stnNm"),
                        a.temperature.as("temperature"),
                        a.humidity.as("humidity"),
                        a.pm10.as("pm10"),
                        a.pm25.as("pm25")))
                .from(a)
                .join(b)
                .on(a.ktDataKey.devId.eq(b.devId))
                .where(
                        a.ktDataKey.equipDate.between(params.getStartDatetime(), params.getEndDatetime())
                        .and(a.ktDataKey.devId.eq(params.getStnNm()))
                        .and(params.getPm10().get(1) == null ?
                                a.pm10.goe(params.getPm10().get(0)) :
                                a.pm10.between(params.getPm10().get(0), params.getPm10().get(1)))
                        .and(params.getPm25().get(1) == null ?
                                a.pm25.goe(params.getPm25().get(0)) :
                                a.pm25.between(params.getPm25().get(0), params.getPm25().get(1))))
                .fetch();
    }
}
