package com.kbd.PAB.Service;


import com.kbd.PAB.Repository.BbsRepository;
import com.kbd.PAB.VO.BbsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BbsService {

    @Autowired
    BbsRepository bbsRepository;

    public int writeBbs(BbsVO vo) {
        try{
            Date currentDate = new Date();
            if(vo.getWriteDate() == null) {
                vo.setWriteDate(currentDate);
            } else {
                vo.setUpdateDate(currentDate);
            }

            bbsRepository.save(vo);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return -1;
        }
    }

    public List<BbsVO> readAllBbs() {
        List<BbsVO> vos = bbsRepository.findAll();
        return vos;
    }

    public BbsVO readByBbsID(int bbsID) {
        Optional<BbsVO> oVO = bbsRepository.findById(bbsID);
        if(oVO.isPresent()) {
            BbsVO vo = isRead(oVO.get());
            return vo;
        } else {
            return null;
        }
    }

    public BbsVO isRead(BbsVO vo) {
        vo.setViews(vo.getViews() + 1);
        return vo;
    }

}
