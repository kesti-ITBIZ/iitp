package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import kr.co.kesti.iitp.repository.AirkoreaDataRepository;
import kr.co.kesti.iitp.repository.KtDataRepository;
import kr.co.kesti.iitp.repository.ObserverDataRepository;
import kr.co.kesti.iitp.repository.SDoTDataRepository;
import kr.co.kesti.iitp.vo.ResponseVerficationDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerificationService implements GraphQLQueryResolver {
    private final AirkoreaDataRepository airkoreaDataRepository;
    private final KtDataRepository ktDataRepository;
    private final SDoTDataRepository sDoTDataRepository;
    private final ObserverDataRepository observerDataRepository;

    public List<ResponseVerficationDataVO> getVerificationData(final String standard, final String compare, final String startDatetime, final String endDatetime, final String stdStnId, final String stdStnNm, final String compStnId, final String compStnNm) {
        AtomicInteger count = new AtomicInteger();
        AtomicReference<List<ComparativeDataProjection>> standardData = new AtomicReference<>(), compareData = new AtomicReference<>();
        new Thread(() -> {
            standardData.set(getComparison(standard, startDatetime, endDatetime, stdStnId, stdStnNm));
            count.incrementAndGet();
        }).start();
        new Thread(() -> {
            compareData.set(getComparison(compare, startDatetime, endDatetime, compStnId, compStnNm));
            count.incrementAndGet();
        }).start();
        while (count.get() < 2);
        return standardData.get() != null && compareData.get() != null ? joinByDatetime(standardData.get(), compareData.get()) : null;
    }

    private List<ComparativeDataProjection> getComparison(final String keyword, final String startDatetime, final String endDatetime, final String stnId, final String stnNm) {
        switch (keyword) {
            case "airkorea": return this.airkoreaDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
            case "kt": return this.ktDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
            case "sDoT": return this.sDoTDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
            case "observer": return this.observerDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
        }
        return null;
    }

    private List<ResponseVerficationDataVO> joinByDatetime(List<ComparativeDataProjection> a, List<ComparativeDataProjection> b) {
        List<ResponseVerficationDataVO> list = new LinkedList<>();
        if (!a.isEmpty() && !b.isEmpty()) {
            ComparativeDataProjection _a = a.get(0), _b = b.get(0);
            final String stdStnId = _a.getStnId();
            final String stdStnNm = _a.getStnNm();
            final String compStnId = _a.getStnId();
            final String compStnNm = _a.getStnNm();
            for (Iterator<ComparativeDataProjection> i = a.iterator(), j = b.iterator(); i.hasNext() && j.hasNext(); ) {
                if (_a.getDatetime().compareTo(_b.getDatetime()) < 0) {
                    list.add(ResponseVerficationDataVO.builder()
                            .datetime(_a.getDatetime())
                            .stdStnId(_a.getStnId())
                            .stdStnNm(_a.getStnNm())
                            .stdPm10(_a.getPm10())
                            .stdPm25(_a.getPm25())
                            .compStnId(_b.getStnId())
                            .compStnNm(_b.getStnNm())
                            .compPm10(_b.getPm10())
                            .compPm25(_b.getPm25())
                            .build());
                    _a = i.next();
                } else if (_a.getDatetime().compareTo(_b.getDatetime()) > 0) _b = j.next();
                else {
                    list.add(ResponseVerficationDataVO.builder()
                            .datetime(_a.getDatetime())
                            .stdStnId(_a.getStnId())
                            .stdStnNm(_a.getStnNm())
                            .stdPm10(_a.getPm10())
                            .stdPm25(_a.getPm25())
                            .compStnId(_b.getStnId())
                            .compStnNm(_b.getStnNm())
                            .compPm10(_b.getPm10())
                            .compPm25(_b.getPm25())
                            .build());
                    _a = i.next();
                    _b = j.next();
                }
            }
        }
        return list;
    }
}
