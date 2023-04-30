package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.MbRepository;
import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.MbVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MbService {

    @Autowired
    MbRepository mbRepository;

    public void insertCPU(ArrayList<MbVO> mbVOList) {
        mbRepository.saveAll(mbVOList);
    }

    public void insertAmazonImgLink() throws IOException {
        ArrayList<MbVO> mbVOS = new ArrayList<>();
        mbVOS.addAll(mbRepository.findAll());
        for (MbVO VO : mbVOS) {
            String amazonURL = VO.getAmazonLink();
            if(VO.getAmazon_img_link() == null) {
                ImgLinkCrawlingService imgLinkCrawlingService = new ImgLinkCrawlingService(amazonURL);
                VO.setAmazon_img_link(imgLinkCrawlingService.getInfoFromWebPage());
            }
        }
        insertCPU(mbVOS);
    }
}
