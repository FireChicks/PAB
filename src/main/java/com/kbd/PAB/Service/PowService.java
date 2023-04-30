package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.PowRepository;
import com.kbd.PAB.Repository.RamRepository;
import com.kbd.PAB.VO.MbVO;
import com.kbd.PAB.VO.PowerVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class PowService {

    @Autowired
    PowRepository powRepository;

    public void insertCPU(ArrayList<PowerVO> powVOList) {
        powRepository.saveAll(powVOList);
    }

    public void insertAmazonImgLink() throws IOException {
        ArrayList<PowerVO> powVOS = new ArrayList<>();
        powVOS.addAll(powRepository.findAll());
        for (PowerVO VO : powVOS) {
            String amazonURL = VO.getAmazonLink();
            if(VO.getAmazon_img_link() == null) {
                ImgLinkCrawlingService imgLinkCrawlingService = new ImgLinkCrawlingService(amazonURL);
                VO.setAmazon_img_link(imgLinkCrawlingService.getInfoFromWebPage());
            }
        }
        insertCPU(powVOS);
    }
}
