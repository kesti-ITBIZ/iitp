package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QSDoTQCData;
import kr.co.kesti.iitp.dsl.entity.QSDoTStation;
import kr.co.kesti.iitp.entity.SDoTQCData;
import kr.co.kesti.iitp.vo.ResponseSDoTDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SDoTDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SDoTDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(SDoTQCData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseSDoTDataVO> findAllData(
            final String startDatetime,
            final String endDatetime,
            final String stnNm) {
        QSDoTQCData a = QSDoTQCData.sDoTQCData;
        QSDoTStation b = QSDoTStation.sDoTStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseSDoTDataVO.class,
                        a.sDoTQCDataKey.time.as("datetime"),
                        b.stnId.as("stnNm"),
                        a.tmp_qc.as("temperature"),
                        a.reh_qc.as("relativeHumidity"),
                        a.pm10_qc.as("pm10"),
                        a.pm25_qc.as("pm25")))
                .from(a)
                .join(b)
                .on(a.sDoTQCDataKey.stnId.eq(b.stnId))
                .where(
                        a.sDoTQCDataKey.time.between(startDatetime, endDatetime)
                        .and(a.sDoTQCDataKey.stnId.eq(stnNm)))
                .fetch();
    }
}
