package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.StoRepository;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.StorageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StoService {

    @Autowired
    StoRepository stoRepository;


    public void insertSTO(ArrayList<StorageVO> stoVOList) {
        stoRepository.saveAll(stoVOList);
    }

    public void insertAmazonImgLink() throws IOException {
        ArrayList<StorageVO> stoVOS = new ArrayList<>();
        stoVOS.addAll(stoRepository.findAll());
        for (StorageVO stoVO : stoVOS) {
            String amazonURL = stoVO.getAmazon_Link();
            if(stoVO.getAmazon_img_link() == null) {
                ImgLinkCrawlingService imgLinkCrawlingService = new ImgLinkCrawlingService(amazonURL);
                stoVO.setAmazon_img_link(imgLinkCrawlingService.getInfoFromWebPage());
            }
        }
        insertSTO(stoVOS);
    }

    public List<StorageVO> getAllStos() {
        List<StorageVO>vos = stoRepository.findAll();
        return vos;
    }

    public List<StorageVO> getCpuVOSByBrandName(String stoBrand){
        return  stoRepository.findbyBrandName(stoBrand);
    }

    public StorageVO getStorageByStorageName(String storageName) {
        String[] words = storageName.split("\\s+");
        StringBuilder extractedStoName = new StringBuilder();

        for (int i = 0; i < Math.min(5, words.length); i++) {
            extractedStoName.append(words[i]).append(" ");
        }

        String modifiedStoName = extractedStoName.toString().trim();

        return stoRepository.findbyStorageName(modifiedStoName);
    }


    public List<String> getDistinctBrand() {
        return stoRepository.findDistinctBrand();
    }


    public List<String> getDistinctType() {
        return stoRepository.findDistinctSocket();
    }

    public StorageVO getStoByID(int stoID) {
        Optional<StorageVO> vo = stoRepository.findById(stoID);
        if(vo.isPresent()) {
            return vo.get();
        }
        return null;
    }
}
