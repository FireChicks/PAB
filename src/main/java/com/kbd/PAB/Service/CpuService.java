package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.CpusRepository;
import com.kbd.PAB.VO.CpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CpuService {

    @Autowired
    CpusRepository cpusRepository;


    public void insertCPU(ArrayList<CpuVO> cpuVOList) {
        cpusRepository.saveAll(cpuVOList);
    }

    public void insertAmazonImgLink() throws IOException {
        ArrayList<CpuVO> cpuVOS = new ArrayList<>();
        cpuVOS.addAll(cpusRepository.findAll());
        for (CpuVO cpuVO : cpuVOS) {
            String amazonURL = cpuVO.getAmazon_Link();
            if(cpuVO.getAmazon_img_link() == null) {
                ImgLinkCrawlingService imgLinkCrawlingService = new ImgLinkCrawlingService(amazonURL);
                cpuVO.setAmazon_img_link(imgLinkCrawlingService.getInfoFromWebPage());
            }
        }
        insertCPU(cpuVOS);
    }

    public List<CpuVO> getAllCpus() {
        return cpusRepository.findAll();
    }

    public List<CpuVO> getCpuVOSByBrandName(String cpuBrand){
        return  cpusRepository.findbyBrandName(cpuBrand);
    }


    public List<String> getDistinctBrand() {
        return cpusRepository.findDistinctBrand();
    }
    public List<String> getDistinctSocket() {
        return cpusRepository.findDistinctSocket();
    }

    public CpuVO getCpuVOByCpuName(String cpuName) {
        if (cpuName.endsWith("...")) {
            cpuName = cpuName.substring(0, cpuName.length() - 3);
        }
        return cpusRepository.findbyCPUName(cpuName);
    }
}
