package com.kbd.PAB;


import com.kbd.PAB.Service.PowService;
import com.kbd.PAB.VO.PowerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/pow")
public class PowerController {

    @Autowired
    private PowService powService;

    @GetMapping
    public List<PowerVO> getAllCpus(@RequestParam(name = "page", defaultValue = "1")int pageNumber) {
        return powService.getAllPowerVos();
    }

    @GetMapping("/byBrand")
    public List<PowerVO> getByBrandNameCpus(@RequestParam(value = "brand", defaultValue = "AMD")String cpuBrand) {
        return powService.getCpuVOSByBrandName(cpuBrand);
    }

    @GetMapping("/brandCategory")
    public List<String> getBrandCategory() {
        return powService.getDistinctBrand();
    }

    @GetMapping("/wattCategory")
    public List<String> getWattCategory() {
        return powService.getDistinctWatt();
    }

}
