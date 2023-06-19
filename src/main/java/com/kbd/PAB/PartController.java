package com.kbd.PAB;

import com.kbd.PAB.Service.*;
import com.kbd.PAB.VO.*;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class PartController {

    @Autowired
    UserService userService;

    @Autowired
    private ComService comService;

    @Autowired
    private CpuService cpuService;

    @Autowired
    private GpuService gpuService;

    @Autowired
    private PowService powService;

    @Autowired
    private StoService stoService;

    @Autowired
    private MbService mbService;

    @Autowired
    private RamService ramService;

    @RequestMapping("/allPartView")
    public String getAllPart(Model model,@RequestParam(defaultValue = "505") int bbsID){
        List<CpuVO> cpuVOS = cpuService.getAllCpus();
        List<MbVO> mbVOs = mbService.getAllMbs();
        List<RamVO> ramVos = ramService.getAllRamsVO();
        List<StorageVO> storageVOS = stoService.getAllStos();
        List<PowerVO> powerVos = powService.getAllPowerVos();
        List<GpuVO> gpuVOS = gpuService.getAllGpus();

        ArrayList<PartsVO> partsVOS = new ArrayList<PartsVO>();

        for (CpuVO vo:cpuVOS) {
            PartsVO pVO = new PartsVO(vo.getCpuID(), "cpu", vo.getCpuName(), vo.getAmazon_img_link());
            partsVOS.add(pVO);
        }
        for (MbVO vo:mbVOs) {
            PartsVO pVO = new PartsVO(vo.getMbId(), "mb", vo.getMbName(), vo.getAmazon_img_link());
            partsVOS.add(pVO);
        }
        for (RamVO vo: ramVos) {
            PartsVO pVO = new PartsVO(vo.getRamId(), "ram", vo.getRamName(), vo.getAmazon_img_link());
            partsVOS.add(pVO);
        }
        for (StorageVO vo: storageVOS) {
            PartsVO pVO = new PartsVO(vo.getStoID(), "sto", vo.getStoName(), vo.getAmazon_img_link());
            partsVOS.add(pVO);
        }
        for (PowerVO vo: powerVos) {
            PartsVO pVO = new PartsVO(vo.getPowId(), "pow", vo.getPowName(), vo.getAmazon_img_link());
            partsVOS.add(pVO);
        }
        for (GpuVO vo: gpuVOS) {
            PartsVO pVO = new PartsVO(vo.getGpuId(), "gpu", vo.getGpuName(), vo.getAmazon_img_link());
            partsVOS.add(pVO);
        }

        model.addAttribute("partVOS", partsVOS);
        model.addAttribute("bbsID", bbsID);

        return "/partsView/allView";
    }

    @RequestMapping("/cpuView")
    public String getCpu(){
        return "/partsView/cpuView";
    }

    @RequestMapping("/ramView")
    public String getRam(){
        return "/partsView/ramView";
    }

    @RequestMapping("/mbView")
    public String getMb(){
        return "/partsView/mbView";
    }

    @RequestMapping("/gpuView")
    public String getGpu(){
        return "/partsView/gpuView";
    }

    @RequestMapping("/powView")
    public String getPow(){
        return "/partsView/powView";
    }

    @RequestMapping("/stoView")
    public String getStorage(){
        return "/partsView/stoView";
    }

}

