package com.kbd.PAB;


import com.kbd.PAB.Service.RamService;
import com.kbd.PAB.VO.CpuVO;
import com.kbd.PAB.VO.GpuVO;
import com.kbd.PAB.VO.RamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ram")
public class RamController {

    @Autowired
    private RamService ramService;

    @GetMapping
    public List<RamVO> getAllCpus(@RequestParam(name = "page", defaultValue = "1")int pageNumber) {
        return ramService.getAllRamsVO();
    }

    @GetMapping("/getInfo")
    public RamVO getRam(@RequestParam(name = "name")String name) {
        return ramService.getRamByRamName(name);
    }

    @GetMapping("/byBrand")
    public List<RamVO> getByBrandNameCpus(@RequestParam(value = "brand", defaultValue = "AMD")String cpuBrand) {
        return ramService.getCpuVOSByBrandName(cpuBrand);
    }

    @GetMapping("/brandCategory")
    public List<String> getBrandCategory() {
        return ramService.getDistinctBrand();
    }

    @GetMapping("/get")
    public RamVO getRam(@RequestParam(name = "ramID")int ramID) {
        return ramService.getRamByID(ramID);
    }

}
