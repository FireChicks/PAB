package com.kbd.PAB.Service;


import com.kbd.PAB.Nor.Searchable;
import com.kbd.PAB.Repository.BbsRepository;
import com.kbd.PAB.VO.BbsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<BbsVO> findByPageBbs(Pageable pageable, Searchable searchable) {
        String category = searchable.getSearchCategory();
        if(category.equals("bbsTitle")){
            return bbsRepository.findByBbsTitlePage(pageable, searchable.getSearchText());
        } else if(category.equals("bbsContent")){
            return bbsRepository.findByBbsContentPage(pageable, searchable.getSearchText());
        } else if(category.equals("bbsWriter")) {
            return bbsRepository.findByBbsWriterPage(pageable, searchable.getSearchText());
        } else {
            return bbsRepository.findByPage(pageable);
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

    public int countBbs() {
        return Long.valueOf(bbsRepository.count()).intValue(); // count는 long 타입으로 반환 이를 int로 형변환
    }

    public int pageSize() {
        int maxCount = countBbs();
        int pageSize = (maxCount - 1) / 10;

        if (pageSize > 0){
            pageSize = 1;
        }
        return pageSize;
    }

    public void deleteBbs(int bbsID) {
        bbsRepository.deleteById(bbsID);
    }

}
