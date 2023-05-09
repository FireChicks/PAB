package com.kbd.PAB.Service;


import com.kbd.PAB.Repository.ComEstimateRepository;
import com.kbd.PAB.VO.ComEstimateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.apigateway.model.Op;

import java.util.Optional;


@Service
public class ComEstimateService {

    @Autowired
    ComEstimateRepository comEstimateRepository;

    public ComEstimateVO selectEstimateOne(int comEstimateID) {
        Optional<ComEstimateVO> comEstimateVO = comEstimateRepository.selectEstimateOne(comEstimateID);
        return comEstimateVO.get();
    }

}
