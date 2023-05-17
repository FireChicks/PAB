package com.kbd.PAB.Service;


import com.kbd.PAB.Repository.BbsEstimateRepository;
import com.kbd.PAB.VO.BbsEstimateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BbsEstimateService {

    @Autowired
    BbsEstimateRepository bbsEstimateRepository;

    public BbsEstimateVO selectEstimateOne(int comEstimateID) {
        Optional<BbsEstimateVO> BbsEstimateVO = bbsEstimateRepository.selectEstimateOne(comEstimateID);
        return BbsEstimateVO.get();
    }

    public List<BbsEstimateVO> findByUserID(String userID) {
        return bbsEstimateRepository.findbyUserID(userID);
    }

    public int saveEstimate(BbsEstimateVO vo) throws Exception {
            bbsEstimateRepository.save(vo);
            return 1;
    }

    public int findMaxBbs() {
        if(bbsEstimateRepository.findMaxBbsID() == null) {
            return 0;
        } else {
            return bbsEstimateRepository.findMaxBbsID();
        }
    }

    public BbsEstimateVO findByEstimateID(int EstimateID) {
        return bbsEstimateRepository.findById(EstimateID).get();
    }

    public int deleteEstimate(int EstimateID, String userID) {

        if(isUserPosEstimate(EstimateID, userID)) {
            bbsEstimateRepository.deleteById(EstimateID);
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isUserPosEstimate(int EstimateID, String userID) {
        List<BbsEstimateVO> vos = bbsEstimateRepository.findbyUserID(userID);
        for (BbsEstimateVO vo: vos)
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
