package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import kr.co.kesti.iitp.repository.AirkoreaDataRepository;
import kr.co.kesti.iitp.repository.KtDataRepository;
import kr.co.kesti.iitp.repository.ObserverDataRepository;
import kr.co.kesti.iitp.repository.SDoTDataRepository;
import kr.co.kesti.iitp.repository.VerificationRepository;
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
    private final VerificationRepository verificationRepository;

    public List<ResponseVerficationDataVO> getVerificationData(final String standard, final String compare, final String startDatetime, final String endDatetime, final String stdStnId, final String stdStnNm, final String compStnId, final String compStnNm, final String dateType) {

        if (dateType.equals("hour")) {
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
        } else {
            List<ResponseVerficationDataVO> list = new LinkedList<>();
            list = verificationRepository.findVerificationData(compare, startDatetime, endDatetime, stdStnId, stdStnNm, compStnId, compStnNm);
            return list;
        }
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
        ComparativeDataProjection _a = a.get(0), _b = b.get(0);
        final String stdStnId = _a.getStnId();
        final String stdStnNm = _a.getStnNm();
        final String compStnId = _b.getStnId();
        final String compStnNm = _b.getStnNm();
        int i, j;
        for (i = 0, j = 0; i < a.size() && j < b.size();) {
            if (a.get(i).getDatetime().compareTo(b.get(j).getDatetime()) < 0) {
                list.add(ResponseVerficationDataVO.builder()
                        .datetime(a.get(i).getDatetime())
                        .stdStnId(stdStnId)
                        .stdStnNm(stdStnNm)
                        .stdPm10(a.get(i).getPm10())
                        .stdPm25(a.get(i).getPm25())
                        .compStnId(compStnId)
                        .compStnNm(compStnNm)
                        .compPm10(null)
                        .compPm25(null)
                        .build());
                ++i;
            } else if (a.get(i).getDatetime().compareTo(b.get(j).getDatetime()) > 0) {
                list.add(ResponseVerficationDataVO.builder()
                        .datetime(b.get(j).getDatetime())
                        .stdStnId(stdStnId)
                        .stdStnNm(stdStnNm)
                        .stdPm10(null)
                        .stdPm25(null)
                        .compStnId(compStnId)
                        .compStnNm(compStnNm)
                        .compPm10(b.get(j).getPm10())
                        .compPm25(b.get(j).getPm25())
                        .build());
                ++j;
            } else {
                list.add(ResponseVerficationDataVO.builder()
                        .datetime(a.get(i).getDatetime())
                        .stdStnId(stdStnId)
                        .stdStnNm(stdStnNm)
                        .stdPm10(a.get(i).getPm10())
                        .stdPm25(a.get(i).getPm25())
                        .compStnId(compStnId)
                        .compStnNm(compStnNm)
                        .compPm10(b.get(j).getPm10())
                        .compPm25(b.get(j).getPm25())
                        .build());
                ++i;
                ++j;
            }
        }
        for (; i < a.size(); ++i)
            list.add(ResponseVerficationDataVO.builder()
                    .datetime(a.get(i).getDatetime())
                    .stdStnId(stdStnId)
                    .stdStnNm(stdStnNm)
                    .stdPm10(a.get(i).getPm10())
                    .stdPm25(a.get(i).getPm25())
                    .compStnId(compStnId)
                    .compStnNm(compStnNm)
                    .compPm10(null)
                    .compPm25(null)
                    .build());
        for (; j < b.size(); ++j)
            list.add(ResponseVerficationDataVO.builder()
                    .datetime(b.get(j).getDatetime())
                    .stdStnId(stdStnId)
                    .stdStnNm(stdStnNm)
                    .stdPm10(null)
                    .stdPm25(null)
                    .compStnId(compStnId)
                    .compStnNm(compStnNm)
                    .compPm10(b.get(j).getPm10())
                    .compPm25(b.get(j).getPm25())
                    .build());
        return list;
    }
}
