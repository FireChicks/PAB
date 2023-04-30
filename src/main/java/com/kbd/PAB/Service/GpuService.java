package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.GpuRepository;
import com.kbd.PAB.Repository.RamRepository;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class GpuService {

    @Autowired
    GpuRepository gpuRepository;

    public void insertCPU(ArrayList<GpuVO> gpuVOList) {
        gpuRepository.saveAll(gpuVOList);
    }

    public void insertAmazonImgLink() throws IOException {
        ArrayList<GpuVO> gpuVOS = new ArrayList<>();
        gpuVOS.addAll(gpuRepository.findAll());
        for (GpuVO VO : gpuVOS) {
            String amazonURL = VO.getAmazonLink();
            if(VO.getAmazon_img_link() == null) {
                ImgLinkCrawlingService imgLinkCrawlingService = new ImgLinkCrawlingService(amazonURL);
                VO.setAmazon_img_link(imgLinkCrawlingService.getInfoFromWebPage());
            }
        }
        insertCPU(gpuVOS);
    }
}