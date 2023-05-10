package com.kbd.PAB.Service;


import com.kbd.PAB.Repository.ComEstimateRepository;
import com.kbd.PAB.VO.ComEstimateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.apigateway.model.Op;

import java.util.List;
import java.util.Optional;


@Service
public class ComEstimateService {

    @Autowired
    ComEstimateRepository comEstimateRepository;

    public ComEstimateVO selectEstimateOne(int comEstimateID) {
        Optional<ComEstimateVO> comEstimateVO = comEstimateRepository.selectEstimateOne(comEstimateID);
        return comEstimateVO.get();
    }

    public List<ComEstimateVO> findByUserID(String userID) {
        return comEstimateRepository.findbyUserID(userID);
    }

    public int saveEstimate(ComEstimateVO vo) throws Exception {
        if(comEstimateRepository.findbyUserID(vo.getUserID()).size() <= 3) { //최대 견적 개수 3개 이하에서만 새로 생성 가능
            comEstimateRepository.save(vo);
            return 1; // 정상 저장
        } else {
            return -1; //개수 초과
        }

    }

}
