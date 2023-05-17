package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.RamRepository;
import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.MbVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RamService {

    @Autowired
    RamRepository ramRepository;

    public void insertCPU(ArrayList<RamVO> ramVOList) {
        ramRepository.saveAll(ramVOList);
    }

    public List<RamVO> getAllRamsVO() {  return ramRepository.findAll();}

    public List<RamVO> getCpuVOSByBrandName(String cpuBrand){
        return  ramRepository.findbyBrandName(cpuBrand);
    }

    public RamVO getRamByRamName(String ramName) {
        String[] words = ramName.split("\\s+");
        StringBuilder extractedRamName = new StringBuilder();

        for (int i = 0; i < Math.min(5, words.length); i++) {
            extractedRamName.append(words[i]).append(" ");
        }

        String modifiedRamName = extractedRamName.toString().trim();

        return ramRepository.findbyRamName(modifiedRamName);
    }


    public List<String> getDistinctBrand() {
        return ramRepository.findDistinctBrand();
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
