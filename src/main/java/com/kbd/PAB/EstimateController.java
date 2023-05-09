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
        ComEstimateVO comEstimateVO = new ComEstimateVO();
        if(infoName.equals("cpuName")) {
            comEstimateVO.setCpuName(info);
            if (session.getAttribute("mainBoard") != null) {
                comEstimateVO.setMainBoard(session.getAttribute("mainBoard").toString());
            }
            if (session.getAttribute("ram") != null) {
                comEstimateVO.setRam(session.getAttribute("ram").toString());
            }
            if (session.getAttribute("storage") != null) {
                comEstimateVO.setStorage(session.getAttribute("storage").toString());
            }
            if (session.getAttribute("power") != null) {
                comEstimateVO.setPower(session.getAttribute("power").toString());
            }
            if (session.getAttribute("gpu") != null) {
                comEstimateVO.setGpu(session.getAttribute("gpu").toString());
            }
        }else if(infoName.equals("mainBoard")) {
            comEstimateVO.setMainBoard(info);
            if (session.getAttribute("cpuName") != null) {
                comEstimateVO.setCpuName(session.getAttribute("cpuName").toString());
            }
            if (session.getAttribute("ram") != null) {
                comEstimateVO.setRam(session.getAttribute("ram").toString());
            }
            if (session.getAttribute("storage") != null) {
                comEstimateVO.setStorage(session.getAttribute("storage").toString());
            }
            if (session.getAttribute("power") != null) {
                comEstimateVO.setPower(session.getAttribute("power").toString());
            }
            if (session.getAttribute("gpu") != null) {
                comEstimateVO.setGpu(session.getAttribute("gpu").toString());
            }
        }else if(infoName.equals("ram")) {
            comEstimateVO.setRam(info);
            if (session.getAttribute("cpuName") != null) {
                comEstimateVO.setCpuName(session.getAttribute("cpuName").toString());
            }
            if (session.getAttribute("mainBoard") != null) {
                comEstimateVO.setMainBoard(session.getAttribute("mainBoard").toString());
            }
            if (session.getAttribute("storage") != null) {
                comEstimateVO.setStorage(session.getAttribute("storage").toString());
            }
            if (session.getAttribute("power") != null) {
                comEstimateVO.setPower(session.getAttribute("power").toString());
            }
            if (session.getAttribute("gpu") != null) {
                comEstimateVO.setGpu(session.getAttribute("gpu").toString());
            }
        }else if(infoName.equals("storage")) {
            comEstimateVO.setStorage(info);
            if (session.getAttribute("cpuName") != null) {
                comEstimateVO.setCpuName(session.getAttribute("cpuName").toString());
            }
            if (session.getAttribute("mainBoard") != null) {
                comEstimateVO.setMainBoard(session.getAttribute("mainBoard").toString());
            }
            if (session.getAttribute("ram") != null) {
                comEstimateVO.setRam(session.getAttribute("ram").toString());
            }
            if (session.getAttribute("power") != null) {
                comEstimateVO.setPower(session.getAttribute("power").toString());
            }
            if (session.getAttribute("gpu") != null) {
                comEstimateVO.setGpu(session.getAttribute("gpu").toString());
            }
        }else if(infoName.equals("power")) {
            comEstimateVO.setPower(info);
            if (session.getAttribute("cpuName") != null) {
                comEstimateVO.setCpuName(session.getAttribute("cpuName").toString());
            }
            if (session.getAttribute("mainBoard") != null) {
                comEstimateVO.setMainBoard(session.getAttribute("mainBoard").toString());
            }
            if (session.getAttribute("ram") != null) {
                comEstimateVO.setRam(session.getAttribute("ram").toString());
            }
            if (session.getAttribute("storage") != null) {
                comEstimateVO.setStorage(session.getAttribute("storage").toString());
            }
            if (session.getAttribute("gpu") != null) {
                comEstimateVO.setGpu(session.getAttribute("gpu").toString());
            }
        }else if(infoName.equals("gpu")) {
            if (session.getAttribute("cpuName") != null) {
                comEstimateVO.setCpuName(session.getAttribute("cpuName").toString());
            }
            if (session.getAttribute("mainBoard") != null) {
                comEstimateVO.setMainBoard(session.getAttribute("mainBoard").toString());
            }
            if (session.getAttribute("ram") != null) {
                comEstimateVO.setRam(session.getAttribute("ram").toString());
            }
            if (session.getAttribute("storage") != null) {
                comEstimateVO.setStorage(session.getAttribute("storage").toString());
            }
            if (session.getAttribute("power") != null) {
                comEstimateVO.setPower(session.getAttribute("power").toString());
            }
            comEstimateVO.setGpu(info);
        }else {
            comEstimateVO = comEstimateService.selectEstimateOne(1);
        }
        session.setAttribute("cpuName", comEstimateVO.getCpuName());
        session.setAttribute("mainBoard", comEstimateVO.getMainBoard());
        session.setAttribute("ram", comEstimateVO.getRam());
        session.setAttribute("storage", comEstimateVO.getStorage());
        session.setAttribute("power", comEstimateVO.getPower());
        session.setAttribute("gpu", comEstimateVO.getGpu());
        return "Estimate/makeEstimate";
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

    @RequestMapping("/stroageParts")
    public String stoParts() {
        return "Estimate/searchStorageParts";
    }

    @RequestMapping("/gpuParts")
    public String gpuParts() {
        return "Estimate/searchGpuParts";
    }
}
