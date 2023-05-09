package com.kbd.PAB.Service;

import com.kbd.PAB.Crawling.ImgLinkCrawlingService;
import com.kbd.PAB.Repository.StoRepository;
import com.kbd.PAB.VO.StorageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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


    public List<String> getDistinctBrand() {
        return stoRepository.findDistinctBrand();
    }


    public List<String> getDistinctType() {
        return stoRepository.findDistinctSocket();
    }
}
