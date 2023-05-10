package com.kbd.PAB;
import com.kbd.PAB.Service.ComEstimateService;
import com.kbd.PAB.VO.ComEstimateVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Configuration
@ComponentScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })
@EntityScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })


@Controller
@RequestMapping("/estimate")
public class EstimateController {

    @Autowired
    private ComEstimateService comEstimateService;

    @RequestMapping("")
    public String estimate(Model model, HttpSession session,@RequestParam(name = "info", defaultValue = "") String info,@RequestParam(name = "infoName", defaultValue = "none") String infoName) {
        ComEstimateVO comEstimateVO = (ComEstimateVO) session.getAttribute("comEstimate");

        if(infoName.equals("cpuName")) {
            comEstimateVO.setCpuName(info);
        }else if(infoName.equals("mainBoard")) {
            comEstimateVO.setMainBoard(info);
        }else if(infoName.equals("ram")) {
            comEstimateVO.setRam(info);
        }else if(infoName.equals("storage")) {
            comEstimateVO.setStorage(info);
        }else if(infoName.equals("power")) {
            comEstimateVO.setPower(info);
        }else if(infoName.equals("gpu")) {
            comEstimateVO.setGpu(info);
        }else { // 들어온게 없을 때
            comEstimateVO = new ComEstimateVO();
            comEstimateVO.setUserID(session.getAttribute("userID").toString());
        }

        session.setAttribute("comEstimate", comEstimateVO);
        return "Estimate/makeEstimate";
    }

    @RequestMapping("/saveAction")
    public String saveAction(HttpSession session) throws Exception {
        ComEstimateVO comEstimateVO = (ComEstimateVO) session.getAttribute("comEstimate");
        int isSaved = comEstimateService.saveEstimate(comEstimateVO);
        if(isSaved == 1) {
            return "index";
        } else {
            return "myEstimate";
        }
    }

    @RequestMapping("/myEstimate")
    public String getMyEstimate(Model model, HttpSession session) {
        if(session.getAttribute("userID") == null) {
            return "index";
        }
        String userID = session.getAttribute("userID").toString();
        List<ComEstimateVO> vos = comEstimateService.findByUserID(userID);
        model.addAttribute("estimateList", vos);
        return "myEstimate";
    }

    @RequestMapping("/cpuParts")
    public String cpuParts() {
        return "Estimate/searchCpuParts";
    }

    @RequestMapping("/mbParts")
    public String mbParts() {
        return "Estimate/searchMbParts";
    }

    @RequestMapping("/ramParts")
    public String ramParts() {
        return "Estimate/searchRamParts";
    }

    @RequestMapping("/powerParts")
    public String powerParts() {
        return "Estimate/searchPowerParts";
    }

    @RequestMapping("/storageParts")
    public String stoParts() {
        return "Estimate/searchStorageParts";
    }

    @RequestMapping("/gpuParts")
    public String gpuParts() {
        return "Estimate/searchGpuParts";
    }
}
