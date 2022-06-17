package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QAirkoreaQCData;
import kr.co.kesti.iitp.dsl.entity.QAirkoreaStation;
import kr.co.kesti.iitp.entity.AirkoreaQCData;
import kr.co.kesti.iitp.vo.ResponseAirkoreaDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class AirkoreaDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public AirkoreaDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(AirkoreaQCData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseAirkoreaDataVO> findAllData(
            final String startDatetime,
            final String endDatetime,
            final String stnNm) {
        QAirkoreaQCData a = QAirkoreaQCData.airkoreaQCData;
        QAirkoreaStation b = QAirkoreaStation.airkoreaStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseAirkoreaDataVO.class,
                        a.airkoreaQCDataKey.time.as("datetime"),
                        a.airkoreaQCDataKey.stnId.as("stnNm"),
                        a.so2.floatValue().as("so2"),
                        a.co.floatValue().as("co"),
                        a.o3.floatValue().as("o3"),
                        a.no2.floatValue().as("no2"),
                        a.pm10_qc.floatValue().as("pm10"),
                        a.pm25_qc.floatValue().as("pm25")
                ))
                .from(a)
                .join(b)
                .on(a.airkoreaQCDataKey.stnId.eq(b.stnNm))
                .where(
                        a.airkoreaQCDataKey.time.between(startDatetime, endDatetime)
                        .and(a.airkoreaQCDataKey.stnId.eq(stnNm))
                        .and(a.so2.gt(-900))
                        .and(a.co.gt(-900))
                        .and(a.o3.gt(-900))
                        .and(a.no2.gt(-900))
                        .and(a.pm10_qc.gt(-900))
                        .and(a.pm25_qc.gt(-900)))
                .fetch();
    }
}
