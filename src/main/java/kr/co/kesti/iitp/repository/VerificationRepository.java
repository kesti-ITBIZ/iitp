package kr.co.kesti.iitp.repository;

import kr.co.kesti.iitp.vo.ResponseVerficationDataVO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VerificationRepository {

    @PersistenceContext
    EntityManager em;

    public List<ResponseVerficationDataVO> findVerificationData(final String compare,
                                                                final String startDatetime,
                                                                final String endDatetime,
                                                                final String stdStnId,
                                                                final String stdStnNm,
                                                                final String compStnId,
                                                                final String compStnNm) {

        List<ResponseVerficationDataVO> list = new ArrayList<>();
        List<Object[]> lst = null;

        if (compare.equals("sDoT")) {
            // 서울시
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaDataKey.stnNm as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10 as stdPm10, " +
                    "    a.pm25 as stdPm25, " +
                    "    c.sDoTDataKey.modelSr as compStnId, " +
                    "    d.stnId as compStnNm, " +
                    "    c.pm10 as compPm10, " +
                    "    c.pm25 as compPm25 " +
                    "from AirkoreaData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaDataKey.stnNm = b.stnNm " +
                    "inner join SDoTData c " +
                    "on a.airkoreaDataKey.time = c.sDoTDataKey.registTime " +
                    "inner join SDoTStation d " +
                    "on c.sDoTDataKey.modelSr = d.stnId " +
                    "where a.airkoreaDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaDataKey.stnNm = :stdStnId " +
                    "   or a.airkoreaDataKey.stnNm = :stdStnNm) " +
                    "and ( " +
                    "   c.sDoTDataKey.modelSr = :compStnId " +
                    "   or d.stnId = :compStnNm) " +
                    "and c.sDoTDataKey.div = 3 " +
                    "and (a.pm10 is not null and c.pm10 is not null and a.pm10 <> -999 and c.pm10 <> -999)" +
                    "and (a.pm25 is not null and c.pm25 is not null and a.pm25 <> -999 and c.pm25 <> -999)" +
                    "order by a.airkoreaDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else if(compare.equals("kt")) {
            // KT
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaDataKey.stnNm as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10 as stdPm10, " +
                    "    a.pm25 as stdPm25, " +
                    "    c.ktDataKey.devId as compStnId, " +
                    "    d.devId as compStnNm, " +
                    "    c.pm10 as compPm10, " +
                    "    c.pm25 as compPm25 " +
                    "from AirkoreaData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaDataKey.stnNm = b.stnNm " +
                    "inner join KTData c " +
                    "on function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI') = c.ktDataKey.equipDate " +
                    "inner join KTStation d " +
                    "on c.ktDataKey.devId = d.devId " +
                    "where a.airkoreaDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaDataKey.stnNm = :stdStnId " +
                    "   or a.airkoreaDataKey.stnNm = :stdStnNm) " +
                    "and ( " +
                    "   c.ktDataKey.devId = :compStnId " +
                    "   or d.devNm = :compStnNm) " +
                    "and (a.pm10 is not null and c.pm10 is not null and a.pm10 <> -999 and c.pm10 <> -999)" +
                    "and (a.pm25 is not null and c.pm25 is not null and a.pm25 <> -999 and c.pm25 <> -999)" +
                    "order by a.airkoreaDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else {
            // 옵저버
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaDataKey.stnNm as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10 as stdPm10, " +
                    "    a.pm25 as stdPm25, " +
                    "    c.observerDataKey.stnSerial as compStnId, " +
                    "    d.stnNm as compStnNm, " +
                    "    -999.f as compPm10, " +
                    "    c.pm25 as compPm25 " +
                    "from AirkoreaData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaDataKey.stnNm = b.stnNm " +
                    "inner join ObserverData c " +
                    "on function('to_timestamp', a.airkoreaDataKey.time, 'YYYY-MM-DD HH24:MI') = c.observerDataKey.dataTime " +
                    "inner join ObserverStation d " +
                    "on c.observerDataKey.stnSerial = d.stnSerial " +
                    "where a.airkoreaDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaDataKey.stnNm = :stdStnId " +
                    "   or a.airkoreaDataKey.stnNm = :stdStnNm) " +
                    "and ( " +
                    "   c.observerDataKey.stnSerial = :compStnId " +
                    "   or d.stnNm = :compStnNm) " +
                    "and (a.pm25 is not null and c.pm25 is not null and a.pm25 <> -999 and c.pm25 <> -999)" +
                    "order by a.airkoreaDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        }

        for (int i=0; i<lst.size(); i++) {
            Object[] buff = lst.get(i);
            list.add(ResponseVerficationDataVO.builder()
                    .datetime(buff[0].toString())
                    .stdStnId(buff[1].toString())
                    .stdStnNm(buff[2].toString())
                    .stdPm10(buff[3] != null ? Float.parseFloat(buff[3].toString()) : null)
                    .stdPm25(buff[4] != null ? Float.parseFloat(buff[4].toString()) : null)
                    .compStnId(buff[5].toString())
                    .compStnNm(buff[6].toString())
                    .compPm10(buff[7] != null ? Float.parseFloat(buff[7].toString()) : null)
                    .compPm25(buff[8] != null ? Float.parseFloat(buff[8].toString()) : null)
                    .build());
        }

        return list;
    }

    public List<ResponseVerficationDataVO> findVerificationPastData(final String compare,
                                                                final String startDatetime,
                                                                final String endDatetime,
                                                                final String stdStnId,
                                                                final String stdStnNm,
                                                                final String compStnId,
                                                                final String compStnNm) {

        List<ResponseVerficationDataVO> list = new ArrayList<>();
        List<Object[]> lst = null;

        if (compare.equals("sDoT")) {
            // 서울시
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaPastDataKey.stnNm as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10 as stdPm10, " +
                    "    a.pm25 as stdPm25, " +
                    "    c.sDoTDataKey.modelSr as compStnId, " +
                    "    d.stnId as compStnNm, " +
                    "    c.pm10 as compPm10, " +
                    "    c.pm25 as compPm25 " +
                    "from AirkoreaPastData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaPastDataKey.stnNm = b.stnNm " +
                    "inner join SDoTData c " +
                    "on a.airkoreaPastDataKey.dataDatetime = c.sDoTDataKey.registTime " +
                    "inner join SDoTStation d " +
                    "on c.sDoTDataKey.modelSr = d.stnId " +
                    "where a.airkoreaPastDataKey.dataDatetime " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaPastDataKey.stnNm = :stdStnId " +
                    "   or a.airkoreaPastDataKey.stnNm = :stdStnNm) " +
                    "and ( " +
                    "   c.sDoTDataKey.modelSr = :compStnId " +
                    "   or d.stnId = :compStnNm) " +
                    "and c.sDoTDataKey.div = 3 " +
                    "and (a.pm10 is not null and c.pm10 is not null and a.pm10 <> -999 and c.pm10 <> -999)" +
                    "and (a.pm25 is not null and c.pm25 is not null and a.pm25 <> -999 and c.pm25 <> -999)" +
                    "order by a.airkoreaPastDataKey.dataDatetime")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else if(compare.equals("kt")) {
            // KT
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaPastDataKey.stnNm as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10 as stdPm10, " +
                    "    a.pm25 as stdPm25, " +
                    "    c.ktDataKey.devId as compStnId, " +
                    "    d.devId as compStnNm, " +
                    "    c.pm10 as compPm10, " +
                    "    c.pm25 as compPm25 " +
                    "from AirkoreaPastData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaPastDataKey.stnNm = b.stnNm " +
                    "inner join KTData c " +
                    "on function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI') = c.ktDataKey.equipDate " +
                    "inner join KTStation d " +
                    "on c.ktDataKey.devId = d.devId " +
                    "where a.airkoreaPastDataKey.dataDatetime " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaPastDataKey.stnNm = :stdStnId " +
                    "   or a.airkoreaPastDataKey.stnNm = :stdStnNm) " +
                    "and ( " +
                    "   c.ktDataKey.devId = :compStnId " +
                    "   or d.devNm = :compStnNm) " +
                    "and (a.pm10 is not null and c.pm10 is not null and a.pm10 <> -999 and c.pm10 <> -999)" +
                    "and (a.pm25 is not null and c.pm25 is not null and a.pm25 <> -999 and c.pm25 <> -999)" +
                    "order by a.airkoreaPastDataKey.dataDatetime")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else {
            // 옵저버
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaPastDataKey.stnNm as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10 as stdPm10, " +
                    "    a.pm25 as stdPm25, " +
                    "    c.observerDataKey.stnSerial as compStnId, " +
                    "    d.stnNm as compStnNm, " +
                    "    -999.f as compPm10, " +
                    "    c.pm25 as compPm25 " +
                    "from AirkoreaPastData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaPastDataKey.stnNm = b.stnNm " +
                    "inner join ObserverData c " +
                    "on function('to_timestamp', a.airkoreaPastDataKey.dataDatetime, 'YYYY-MM-DD HH24:MI') = c.observerDataKey.dataTime " +
                    "inner join ObserverStation d " +
                    "on c.observerDataKey.stnSerial = d.stnSerial " +
                    "where a.airkoreaPastDataKey.dataDatetime " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaPastDataKey.stnNm = :stdStnId " +
                    "   or a.airkoreaPastDataKey.stnNm = :stdStnNm) " +
                    "and ( " +
                    "   c.observerDataKey.stnSerial = :compStnId " +
                    "   or d.stnNm = :compStnNm) " +
                    "and (a.pm25 is not null and c.pm25 is not null and a.pm25 <> -999 and c.pm25 <> -999)" +
                    "order by a.airkoreaPastDataKey.dataDatetime")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        }

        for (int i=0; i<lst.size(); i++) {
            Object[] buff = lst.get(i);
            list.add(ResponseVerficationDataVO.builder()
                    .datetime(buff[0].toString())
                    .stdStnId(buff[1].toString())
                    .stdStnNm(buff[2].toString())
                    .stdPm10(buff[3] != null ? Float.parseFloat(buff[3].toString()) : null)
                    .stdPm25(buff[4] != null ? Float.parseFloat(buff[4].toString()) : null)
                    .compStnId(buff[5].toString())
                    .compStnNm(buff[6].toString())
                    .compPm10(buff[7] != null ? Float.parseFloat(buff[7].toString()) : null)
                    .compPm25(buff[8] != null ? Float.parseFloat(buff[8].toString()) : null)
                    .build());
        }

        return list;
    }

}
