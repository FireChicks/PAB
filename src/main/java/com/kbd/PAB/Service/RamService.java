package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.RamRepository;
import com.kbd.PAB.VO.MbVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class RamService {

    @Autowired
    RamRepository ramRepository;

    public void insertCPU(ArrayList<RamVO> ramVOList) {
        ramRepository.saveAll(ramVOList);
    }

    public void insertAmazonImgLink() throws IOException {
        ArrayList<RamVO> ramVOS = new ArrayList<>();
        ramVOS.addAll(ramRepository.findAll());
        for (RamVO VO : ramVOS) {
            String amazonURL = VO.getAmazonLink();
            if(VO.getAmazon_img_link() == null) {
                ImgLinkCrawlingService imgLinkCrawlingService = new ImgLinkCrawlingService(amazonURL);
                VO.setAmazon_img_link(imgLinkCrawlingService.getInfoFromWebPage());
            }
        }
        insertCPU(ramVOS);
    }
}