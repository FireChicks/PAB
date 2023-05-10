package com.kbd.PAB;


import com.kbd.PAB.Service.StoService;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.StorageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/sto")
public class StoController {

    @Autowired
    private StoService stoService;

    @GetMapping
    public List<StorageVO> getAllStos(@RequestParam(name = "page", defaultValue = "1")int pageNumber) {
        return stoService.getAllStos();
    }

    @GetMapping("/getInfo")
    public StorageVO getStorage(@RequestParam(name = "name")String name) {
        return stoService.getStorageByStorageName(name);
    }

    @GetMapping("/byBrand")
    public List<StorageVO> getByBrandNameCpus(@RequestParam(value = "brand", defaultValue = "AMD")String cpuBrand) {
        return stoService.getCpuVOSByBrandName(cpuBrand);
    }

    @GetMapping("/brandCategory")
    public List<String> getBrandCategory() {
        return stoService.getDistinctBrand();
    }

    @GetMapping("/typeCategory")
    public List<String> getStoTypeCategory() {
        return stoService.getDistinctType();
    }

}
