package com.kbd.PAB.Service;


import com.kbd.PAB.Nor.Searchable;
import com.kbd.PAB.Repository.BbsRepository;
import com.kbd.PAB.Repository.ComRepository;
import com.kbd.PAB.VO.BbsVO;
import com.kbd.PAB.VO.ComVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ComService {

    @Autowired
    ComRepository comRepository;

    public int writeComment(ComVO vo) {
        try{
            comRepository.save(vo);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return -1;
        }
    }


    public List<ComVO> readByBbsID(int bbsID) {
        List<ComVO> cVO = comRepository.findByBbsIDCom(bbsID);
        return cVO;
    }

}
