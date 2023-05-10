package com.kbd.PAB;


import com.kbd.PAB.Service.MbService;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.MbVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/mb")
public class MbController {

    @Autowired
    private MbService mbService;

    @GetMapping
    public List<MbVO> getAllCpus(@RequestParam(name = "page", defaultValue = "1")int pageNumber) {
        return mbService.getAllMbs();
    }

    @GetMapping("/getInfo")
    public MbVO getMb(@RequestParam(name = "name")String name) {
        return mbService.getMbByMbName(name);
    }

    @GetMapping("/byBrand")
    public List<MbVO> getByBrandNameCpus(@RequestParam(value = "brand", defaultValue = "ASUS")String cpuBrand) {
        List<MbVO> mbVOs = mbService.getMbVOSByBrandName(cpuBrand);
        return mbVOs;
    }

    @GetMapping("/brandCategory")
    public List<String> getBrandCategory() {
        return mbService.getDistinctBrand();
    }

    @GetMapping("/socketCategory")
    public List<String> getSocketCategory() {
        return mbService.getDistinctSocket();
    }

}
