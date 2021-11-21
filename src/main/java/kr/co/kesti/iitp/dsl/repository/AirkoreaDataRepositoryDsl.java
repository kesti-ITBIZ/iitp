package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
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
            final String stnNm,
            final List<Float> pm10,
            final List<Float> pm25) {
        QAirkoreaData a = QAirkoreaData.airkoreaData;
        QAirkoreaStation b = QAirkoreaStation.airkoreaStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseAirkoreaDataVO.class,
                        a.airkoreaDataKey.time.as("datetime"),
                        a.airkoreaDataKey.stnNm.as("stnNm"),
                        a.so2.as("so2"),
                        a.co.as("co"),
                        a.o3.as("o3"),
                        a.no2.as("no2"),
                        a.pm10.as("pm10"),
                        a.pm25.as("pm25")))
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
                .fetch();
    }
}
