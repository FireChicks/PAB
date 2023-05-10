package com.kbd.PAB;


import com.kbd.PAB.Service.*;
import com.kbd.PAB.VO.CpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/cpu")
public class CpuController {

    @Autowired
    private CpuService cpuService;

    @GetMapping
    public List<CpuVO> getAllCpus(@RequestParam(name = "page", defaultValue = "1")int pageNumber) {
        return cpuService.getAllCpus();
    }

    @GetMapping("/getInfo")
    public CpuVO getCpu(@RequestParam(name = "name")String name) {
        return cpuService.getCpuVOByCpuName(name);
    }

    @GetMapping("/byBrand")
    public List<CpuVO> getByBrandNameCpus(@RequestParam(value = "brand", defaultValue = "AMD")String cpuBrand) {
        return cpuService.getCpuVOSByBrandName(cpuBrand);
    }

    @GetMapping("/brandCategory")
    public List<String> getBrandCategory() {
        return cpuService.getDistinctBrand();
    }

    @GetMapping("/socketCategory")
    public List<String> getSocketCategory() {
        return cpuService.getDistinctSocket();
    }

}
