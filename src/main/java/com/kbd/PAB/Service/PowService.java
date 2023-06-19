package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.PowRepository;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.PowerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PowService {

    @Autowired
    PowRepository powRepository;

    public void insertCPU(ArrayList<PowerVO> powVOList) {
        powRepository.saveAll(powVOList);
    }

    public List<PowerVO> getAllPowerVos() {return powRepository.findAll();}

    public List<PowerVO> getCpuVOSByBrandName(String cpuBrand){
        return  powRepository.findbyBrandName(cpuBrand);
    }

    public PowerVO getPowerByPowerName(String powName) {
        String[] words = powName.split("\\s+");
        StringBuilder extractedPowName = new StringBuilder();

        for (int i = 0; i < Math.min(5, words.length); i++) {
            extractedPowName.append(words[i]).append(" ");
        }

        String modifiedPowName = extractedPowName.toString().trim();

        return powRepository.findbyPowerName(modifiedPowName);
    }


    public List<String> getDistinctBrand() {
        return powRepository.findDistinctBrand();
    }

    public List<String> getDistinctWatt() {
        return powRepository.findDistinctWatt();
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

    public PowerVO getPowerByID(int powID) {
        Optional<PowerVO> vo = powRepository.findById(powID);
        if(vo.isPresent()) {
            return vo.get();
        }
        return null;
    }
}
