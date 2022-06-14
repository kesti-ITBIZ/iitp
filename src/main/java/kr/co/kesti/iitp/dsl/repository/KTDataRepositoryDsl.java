package kr.co.kesti.iitp.dsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kesti.iitp.dsl.entity.QKTQCData;
import kr.co.kesti.iitp.dsl.entity.QKTStation;
import kr.co.kesti.iitp.entity.KTQCData;
import kr.co.kesti.iitp.vo.ResponseKTDataVO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KTDataRepositoryDsl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public KTDataRepositoryDsl(JPAQueryFactory jpaQueryFactory) {
        super(KTQCData.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ResponseKTDataVO> findAllData(
            final String startDatetime,
            final String endDatetime,
            final String stnNm) {
        QKTQCData a = QKTQCData.kTQCData;
        QKTStation b = QKTStation.kTStation;

        return this.jpaQueryFactory
                .select(Projections.constructor(ResponseKTDataVO.class,
                        a.ktQCDataKey.time.as("datetime"),
                        a.ktQCDataKey.stnId.as("stnNm"),
                        a.tmp_qc.as("temperature"),
                        a.reh_qc.as("humidity"),
                        a.pm10_qc.as("pm10"),
                        a.pm25_qc.as("pm25")))
                .from(a)
                .join(b)
                .on(a.ktQCDataKey.stnId.eq(b.devId))
                .where(
                        a.ktQCDataKey.time.between(startDatetime, endDatetime)
                        .and(b.devNm.eq(stnNm)))
                .fetch();
    }
}
