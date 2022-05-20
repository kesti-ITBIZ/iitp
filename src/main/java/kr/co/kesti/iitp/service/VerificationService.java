package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.projection.ComparativeDataProjection;
import kr.co.kesti.iitp.repository.*;
import kr.co.kesti.iitp.vo.ResponseVerficationDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerificationService implements GraphQLQueryResolver {
    private final AirkoreaDataRepository airkoreaDataRepository;
    private final AirkoreaPastDataRepository airkoreaPastDataRepository;
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

            if (Integer.parseInt(startDatetime.substring(0, 4)) <= 2021 && Integer.parseInt(endDatetime.substring(0, 4)) < 2022) {
                // 2018~2021 환경부 데이터
                list = verificationRepository.findVerificationPastData(compare, startDatetime, endDatetime, stdStnId, stdStnNm, compStnId, compStnNm);
            } else if(Integer.parseInt(startDatetime.substring(0, 4)) <= 2021 && Integer.parseInt(endDatetime.substring(0, 4)) >= 2022) {
                // 2018~2022 환경부 데이터
                List<ResponseVerficationDataVO> list1 = new LinkedList<>();
                List<ResponseVerficationDataVO> list2 = new LinkedList<>();

                list1 = verificationRepository.findVerificationPastData(compare, startDatetime, "20211231235959", stdStnId, stdStnNm, compStnId, compStnNm);
                list2 = verificationRepository.findVerificationData(compare, "20220101000000", endDatetime, stdStnId, stdStnNm, compStnId, compStnNm);
                list.addAll(list1);
                list.addAll(list2);
            } else {
                // 2022년 이후 환경부 데이터
                list = verificationRepository.findVerificationData(compare, startDatetime, endDatetime, stdStnId, stdStnNm, compStnId, compStnNm);
            }

            return list;
        }
    }

    private List<ComparativeDataProjection> getComparison(final String keyword, final String startDatetime, final String endDatetime, final String stnId, final String stnNm) {
        switch (keyword) {
            case "airkorea":
                if (Integer.parseInt(startDatetime.substring(0, 4)) <= 2021 && Integer.parseInt(endDatetime.substring(0, 4)) < 2022) {
                    // 2018~2021 환경부 데이터
                    return this.airkoreaPastDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
                } else if(Integer.parseInt(startDatetime.substring(0, 4)) <= 2021 && Integer.parseInt(endDatetime.substring(0, 4)) >= 2022) {
                    // 2018~2022 환경부 데이터
                    List<ComparativeDataProjection> result1 = new LinkedList<>();
                    List<ComparativeDataProjection> result2 = new LinkedList<>();
                    List<ComparativeDataProjection> result = new LinkedList<>();
                    result1 = this.airkoreaPastDataRepository.findAllComparativeData(startDatetime, "20211231235959", stnId, stnNm);
                    result2 = this.airkoreaDataRepository.findAllComparativeData("20220101000000", endDatetime, stnId, stnNm);
                    result.addAll(result1);
                    result.addAll(result2);
                    return result;
                } else {
                    // 2022년 이후 환경부 데이터
                    return this.airkoreaDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
                }
            case "kt": return this.ktDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
            case "sDoT": return this.sDoTDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
            case "observer": return this.observerDataRepository.findAllComparativeData(startDatetime, endDatetime, stnId, stnNm);
        }
        return null;
    }

    private List<ResponseVerficationDataVO> joinByDatetime(List<ComparativeDataProjection> a, List<ComparativeDataProjection> b) {
        List<ResponseVerficationDataVO> list = new LinkedList<>();
        if(a.size() > 0 && b.size() > 0) {
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
        }
        return list;
    }
}
