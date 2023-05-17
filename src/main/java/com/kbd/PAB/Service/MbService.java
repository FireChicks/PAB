package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.MbRepository;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.MbVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MbService {

    @Autowired
    MbRepository mbRepository;

    public void insertMb(ArrayList<MbVO> mbVOList) {
        mbRepository.saveAll(mbVOList);
    }
    public List<MbVO> getAllMbs() {
        return mbRepository.findAll();
    }

    public MbVO getMbByMbName(String mbName) {
        String[] words = mbName.split("\\s+");
        StringBuilder extractedMbName = new StringBuilder();

        for (int i = 0; i < Math.min(5, words.length); i++) {
            extractedMbName.append(words[i]).append(" ");
        }

        String modifiedMbName = extractedMbName.toString().trim();

        return mbRepository.findbyMBName(modifiedMbName);
    }

    public List<MbVO> getMbVOSByBrandName(String cpuBrand){
        return  mbRepository.findbyBrandName(cpuBrand);
    }

    public List<String> getDistinctBrand() {
        return mbRepository.findDistinctBrand();
    }
    public List<String> getDistinctSocket() {
        return mbRepository.findDistinctSocket();
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
        insertMb(mbVOS);
    }
}
