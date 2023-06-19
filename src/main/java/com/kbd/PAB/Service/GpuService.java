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
import java.util.List;
import java.util.Optional;

@Service
public class GpuService {

    @Autowired
    GpuRepository gpuRepository;

    public void insertCPU(ArrayList<GpuVO> gpuVOList) {
        gpuRepository.saveAll(gpuVOList);
    }

    public List<GpuVO> getAllGpus(){ return  gpuRepository.findAll();}

    public List<GpuVO> getCpuVOSByBrandName(String cpuBrand){
        return  gpuRepository.findbyBrandName(cpuBrand);
    }

    public GpuVO getGpuVOByGpuName(String gpuName) {
        String[] words = gpuName.split("\\s+");
        StringBuilder extractedGpuName = new StringBuilder();

        for (int i = 0; i < Math.min(5, words.length); i++) {
            extractedGpuName.append(words[i]).append(" ");
        }

        String modifiedGpuName = extractedGpuName.toString().trim();

        return gpuRepository.findbyGPUName(modifiedGpuName);
    }

    public List<String> getDistinctBrand() {
        return gpuRepository.findDistinctBrand();
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

    public GpuVO getGpuByID(int gpuID) {
        Optional<GpuVO> vo = gpuRepository.findById(gpuID);
        if(vo.isPresent()) {
            return vo.get();
        }
        return null;
    }
}
