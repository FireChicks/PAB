package com.kbd.PAB;


import com.kbd.PAB.Service.GpuService;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.GpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/gpu")
public class GpuController {

    @Autowired
    private GpuService gpuService;

    @GetMapping
    public List<GpuVO> getAllCpus(@RequestParam(name = "page", defaultValue = "1")int pageNumber) {
        return gpuService.getAllGpus();
    }

    @GetMapping("/getInfo")
    public GpuVO getGpu(@RequestParam(name = "name")String name) {
        return gpuService.getGpuVOByGpuName(name);
    }

    @GetMapping("/byBrand")
    public List<GpuVO> getByBrandNameCpus(@RequestParam(value = "brand", defaultValue = "AMD")String cpuBrand) {
        return gpuService.getCpuVOSByBrandName(cpuBrand);
    }

    @GetMapping("/brandCategory")
    public List<String> getBrandCategory() {
        return gpuService.getDistinctBrand();
    }

}
