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
        if(comEstimateRepository.findById(vo.getComEstimateID()).isPresent()) { // 이미 존재하는 견적이라면 그 견적에 저장
            comEstimateRepository.save(vo);
            return 2;
        }
        if(comEstimateRepository.findbyUserID(vo.getUserID()).size() <= 3) { //최대 견적 개수 3개 이하에서만 새로 생성
            comEstimateRepository.save(vo);
            return 1; // 정상 저장
        } else {
            return -1; //개수 초과
        }

    }

    public ComEstimateVO findByEstimateID(int EstimateID) {
        return comEstimateRepository.findById(EstimateID).get();
    }

    public int deleteEstimate(int EstimateID, String userID) {

        if(isUserPosEstimate(EstimateID, userID)) {
            comEstimateRepository.deleteById(EstimateID);
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isUserPosEstimate(int EstimateID, String userID) {
        List<ComEstimateVO> vos = comEstimateRepository.findbyUserID(userID);
        for (ComEstimateVO vo: vos)
        {
            if(vo.getComEstimateID() == EstimateID) {
                if(vo.getUserID().equals(userID)) {             //사용자가 일치할 때 삭제
                    return true;
                }
            }
        }
        return false;
    }

}
