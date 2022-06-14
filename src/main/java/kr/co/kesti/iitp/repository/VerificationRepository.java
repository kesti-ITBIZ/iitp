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
                    "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaQCDataKey.stnId as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10_qc as stdPm10, " +
                    "    a.pm25_qc as stdPm25, " +
                    "    c.sDoTQCDataKey.stnId as compStnId, " +
                    "    d.stnId as compStnNm, " +
                    "    c.pm10_qc as compPm10, " +
                    "    c.pm25_qc as compPm25 " +
                    "from AirkoreaQCData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaQCDataKey.stnId = b.stnNm " +
                    "inner join SDoTQCData c " +
                    "on a.airkoreaQCDataKey.time = c.sDoTQCDataKey.time " +
                    "inner join SDoTStation d " +
                    "on c.sDoTQCDataKey.stnId = d.stnId " +
                    "where a.airkoreaQCDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaQCDataKey.stnId = :stdStnId " +
                    "   or a.airkoreaQCDataKey.stnId = :stdStnNm) " +
                    "and ( " +
                    "   c.sDoTQCDataKey.stnId = :compStnId " +
                    "   or d.stnId = :compStnNm) " +
                    "and (a.pm10_qc is not null and c.pm10_qc is not null and a.pm10_qc > -900 and c.pm10_qc > -900)" +
                    "and (a.pm25_qc is not null and c.pm25_qc is not null and a.pm25_qc > -900 and c.pm25_qc > -900)" +
                    "order by a.airkoreaQCDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else if(compare.equals("kt")) {
            // KT
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaQCDataKey.stnId as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10_qc as stdPm10, " +
                    "    a.pm25_qc as stdPm25, " +
                    "    c.ktQCDataKey.stnId as compStnId, " +
                    "    d.devId as compStnNm, " +
                    "    c.pm10_qc as compPm10, " +
                    "    c.pm25_qc as compPm25 " +
                    "from AirkoreaQCData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaQCDataKey.stnId = b.stnNm " +
                    "inner join KTQCData c " +
                    "on a.airkoreaQCDataKey.time = c.ktQCDataKey.time " +
                    "inner join KTStation d " +
                    "on c.ktQCDataKey.stnId = d.devId " +
                    "where a.airkoreaQCDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaQCDataKey.stnId = :stdStnId " +
                    "   or a.airkoreaQCDataKey.stnId = :stdStnNm) " +
                    "and ( " +
                    "   c.ktQCDataKey.stnId = :compStnId " +
                    "   or d.devNm = :compStnNm) " +
                    "and (a.pm10_qc is not null and c.pm10_qc is not null and a.pm10_qc > -900 and c.pm10_qc > -900)" +
                    "and (a.pm25_qc is not null and c.pm25_qc is not null and a.pm25_qc > -900 and c.pm25_qc > -900)" +
                    "order by a.airkoreaQCDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else {
            // 옵저버
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaQCDataKey.stnId as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10_qc as stdPm10, " +
                    "    a.pm25_qc as stdPm25, " +
                    "    c.observerQCDataKey.stnId as compStnId, " +
                    "    d.stnNm as compStnNm, " +
                    "    -999.f as compPm10, " +
                    "    c.pm25_r_qc as compPm25 " +
                    "from AirkoreaQCData a " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaQCDataKey.stnId = b.stnNm " +
                    "inner join ObserverQCData c " +
                    "on a.airkoreaQCDataKey.time = c.observerQCDataKey.time " +
                    "inner join ObserverStation d " +
                    "on c.observerQCDataKey.stnId = d.stnSerial " +
                    "where a.airkoreaQCDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaQCDataKey.stnId = :stdStnId " +
                    "   or a.airkoreaQCDataKey.stnId = :stdStnNm) " +
                    "and ( " +
                    "   c.observerQCDataKey.stnId = :compStnId " +
                    "   or d.stnNm = :compStnNm) " +
                    "and (a.pm25_qc is not null and c.pm25_r_qc is not null and a.pm25_qc > -900 and c.pm25_r_qc > -900)" +
                    "order by a.airkoreaQCDataKey.time")
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

    public List<ResponseVerficationDataVO> findVerificationHourData(final String compare,
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
                    "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaQCDataKey.stnId as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10_qc as stdPm10, " +
                    "    a.pm25_qc as stdPm25, " +
                    "    c.sDoTQCDataKey.stnId as compStnId, " +
                    "    d.stnId as compStnNm, " +
                    "    c.pm10_qc as compPm10, " +
                    "    c.pm25_qc as compPm25 " +
                    "from AirkoreaQCData a " +
                    "full join SDoTQCData c " +
                    "on a.airkoreaQCDataKey.time = c.sDoTQCDataKey.time " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaQCDataKey.stnId = b.stnNm " +
                    "inner join SDoTStation d " +
                    "on c.sDoTQCDataKey.stnId = d.stnId " +
                    "where a.airkoreaQCDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaQCDataKey.stnId = :stdStnId " +
                    "   or a.airkoreaQCDataKey.stnId = :stdStnNm) " +
                    "and ( " +
                    "   c.sDoTQCDataKey.stnId = :compStnId " +
                    "   or d.stnId = :compStnNm) " +
                    "and (a.pm10_qc is not null and c.pm10_qc is not null and a.pm10_qc > -900 and c.pm10_qc > -900)" +
                    "and (a.pm25_qc is not null and c.pm25_qc is not null and a.pm25_qc > -900 and c.pm25_qc > -900)" +
                    "order by a.airkoreaQCDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else if(compare.equals("kt")) {
            // KT
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaQCDataKey.stnId as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10_qc as stdPm10, " +
                    "    a.pm25_qc as stdPm25, " +
                    "    c.ktQCDataKey.stnId as compStnId, " +
                    "    d.devId as compStnNm, " +
                    "    c.pm10_qc as compPm10, " +
                    "    c.pm25_qc as compPm25 " +
                    "from AirkoreaQCData a " +
                    "full join KTQCData c " +
                    "on a.airkoreaQCDataKey.time = c.ktQCDataKey.time " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaQCDataKey.stnId = b.stnNm " +
                    "inner join KTStation d " +
                    "on c.ktQCDataKey.stnId = d.devId " +
                    "where a.airkoreaQCDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaQCDataKey.stnId = :stdStnId " +
                    "   or a.airkoreaQCDataKey.stnId = :stdStnNm) " +
                    "and ( " +
                    "   c.ktQCDataKey.stnId = :compStnId " +
                    "   or d.devNm = :compStnNm) " +
                    "and (a.pm10_qc is not null and c.pm10_qc is not null and a.pm10_qc > -900 and c.pm10_qc > -900)" +
                    "and (a.pm25_qc is not null and c.pm25_qc is not null and a.pm25_qc > -900 and c.pm25_qc > -900)" +
                    "order by a.airkoreaQCDataKey.time")
                    .setParameter("startDatetime", startDatetime)
                    .setParameter("endDatetime", endDatetime)
                    .setParameter("stdStnId", stdStnId)
                    .setParameter("stdStnNm", stdStnNm)
                    .setParameter("compStnId", compStnId)
                    .setParameter("compStnNm", compStnNm).getResultList();
        } else {
            // 옵저버
            lst = em.createQuery("select " +
                    "    function('to_char', function('to_timestamp', a.airkoreaQCDataKey.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') as datetime, " +
                    "    a.airkoreaQCDataKey.stnId as stdStnId, " +
                    "    b.stnNm as stdStnNm, " +
                    "    a.pm10_qc as stdPm10, " +
                    "    a.pm25_qc as stdPm25, " +
                    "    c.observerQCDataKey.stnId as compStnId, " +
                    "    d.stnNm as compStnNm, " +
                    "    -999.f as compPm10, " +
                    "    c.pm25_r_qc as compPm25 " +
                    "from AirkoreaQCData a " +
                    "full join ObserverQCData c " +
                    "on a.airkoreaQCDataKey.time = c.observerQCDataKey.time " +
                    "inner join AirkoreaStation b " +
                    "on a.airkoreaQCDataKey.stnId = b.stnNm " +
                    "inner join ObserverStation d " +
                    "on c.observerQCDataKey.stnId = d.stnSerial " +
                    "where a.airkoreaQCDataKey.time " +
                    "    between :startDatetime " +
                    "    and :endDatetime " +
                    "and ( " +
                    "   a.airkoreaQCDataKey.stnId = :stdStnId " +
                    "   or a.airkoreaQCDataKey.stnId = :stdStnNm) " +
                    "and ( " +
                    "   c.observerQCDataKey.stnId = :compStnId " +
                    "   or d.stnNm = :compStnNm) " +
                    "and (a.pm25_qc is not null and c.pm25_r_qc is not null and a.pm25_qc > -900 and c.pm25_r_qc > -900)" +
                    "order by a.airkoreaQCDataKey.time")
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
