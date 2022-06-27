package kr.co.kesti.iitp.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import kr.co.kesti.iitp.repository.*;
import kr.co.kesti.iitp.vo.ResponseVerficationDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerificationService implements GraphQLQueryResolver {

    private final VerificationRepository verificationRepository;

    public List<ResponseVerficationDataVO> getVerificationData(final String standard, final String compare, final String startDatetime, final String endDatetime, final String stdStnId, final String stdStnNm, final String compStnId, final String compStnNm, final String dateType) {

        if (dateType.equals("hour")) {
            List<ResponseVerficationDataVO> list = verificationRepository.findVerificationHourData(compare, startDatetime, endDatetime, stdStnId, stdStnNm, compStnId, compStnNm);
            return list;
        } else {
            List<ResponseVerficationDataVO> list = verificationRepository.findVerificationData(compare, startDatetime, endDatetime, stdStnId, stdStnNm, compStnId, compStnNm);
            return list;
        }
    }
}
