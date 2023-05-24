package com.kbd.PAB;
import com.kbd.PAB.Service.ComEstimateService;
import com.kbd.PAB.Service.CpuService;
import com.kbd.PAB.Service.MbService;
import com.kbd.PAB.Service.RamService;
import com.kbd.PAB.VO.ComEstimateVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Configuration
@ComponentScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })
@EntityScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })


@Controller
@RequestMapping("/PortableEstimate")
public class PortableEstimateController {

    @Autowired
    private ComEstimateService comEstimateService;
    @Autowired
    private CpuService cpuService;

    @Autowired
    private MbService mbService;

    @Autowired
    private RamService ramService;
    @RequestMapping("")
    public String estimate(RedirectAttributes rttr, Model model, HttpSession session,
                           @RequestParam(name = "info", defaultValue = "") String info,
                           @RequestParam(name = "infoName", defaultValue = "none") String infoName,
                           @RequestParam(name = "isForceAdd", defaultValue = "none") String isForceAdd,
                           @RequestParam(name="originalPage", defaultValue = "none") String originalPage) {

        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }
        String originPage = "index";

        ComEstimateVO comEstimateVO = (ComEstimateVO) session.getAttribute("comEstimate");

        if(infoName.equals("cpuName")) {
            if(comEstimateVO.getMainBoard() != null) { //메인보드와의 호환성 체크
                String mainBoardSocket = mbService.getMbByMbName(comEstimateVO.getMainBoard()).getMb_cpu_socket();
                String cpuSocket = cpuService.getCpuVOByCpuName(info).getCpu_socket();

                if(mainBoardSocket.equals(cpuSocket)) { //소켓 일치
                    model.addAttribute("actionNotice","성공적으로 CPU를 추가하였습니다.");
                } else {
                    if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                        comEstimateVO.setCpuName(info);
                        comEstimateVO.setMainBoard(null);
                        session.setAttribute("comEstimate", comEstimateVO);
                        return originPage;
                    }
                    rttr.addFlashAttribute("actionNotice", "cpu소켓과 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? CPU 소켓: " + cpuSocket + ", 메인보드 CPU 소켓: " + mainBoardSocket +" 주의: 견적에 저장된 메인보드의 정보가 사라집니다.");
                    rttr.addFlashAttribute("cpuInfo", info);
                    rttr.addFlashAttribute("cpuInfoName", infoName);
                    return "redirect:/PortablEstimate/cpuParts";
                }
            }

            comEstimateVO.setCpuName(info);
        }else if(infoName.equals("mainBoard")) {
            if(comEstimateVO.getCpuName() != null) { //CPU와의 호환성 체크
                String mainBoardSocket = mbService.getMbByMbName(info).getMb_cpu_socket();
                String cpuSocket = cpuService.getCpuVOByCpuName(comEstimateVO.getCpuName()).getCpu_socket();

                if(mainBoardSocket.equals(cpuSocket)) { //소켓 일치
                    model.addAttribute("actionNotice","성공적으로 메인보드를 추가하였습니다.");
                } else {
                    if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                        comEstimateVO.setCpuName(null);
                        comEstimateVO.setRam(null);
                        comEstimateVO.setMainBoard(info);
                        session.setAttribute("comEstimate", comEstimateVO);
                        return originalPage;
                    }
                    rttr.addFlashAttribute("actionNotice", "cpu소켓과 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? CPU 소켓: " + cpuSocket + ", 메인보드 CPU 소켓: " + mainBoardSocket+" 주의: 견적에 저장된 CPU와 RAM의 정보가 사라집니다.");
                    rttr.addFlashAttribute("mbInfo", info);
                    rttr.addFlashAttribute("mbInfoName", infoName);
                    return "redirect:/PortablEstimate/mbParts";
                }

                if(comEstimateVO.getRam() != null) { //Ram과의 호환성 체크
                    String mainBoardRamGen = mbService.getMbByMbName(info).getMbMemGen();
                    String ramGen = ramService.getRamByRamName(comEstimateVO.getRam()).getRamGen();

                    if(mainBoardRamGen.equals(ramGen)) { //소켓 일치
                        model.addAttribute("actionNotice","성공적으로 메인보드를 추가하였습니다.");
                    } else {
                        if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                            comEstimateVO.setCpuName(null);
                            comEstimateVO.setRam(null);
                            comEstimateVO.setMainBoard(info);
                            session.setAttribute("comEstimate", comEstimateVO);
                            return originalPage;
                        }
                        rttr.addFlashAttribute("actionNotice", "Ram세대와 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? 메인보드 ram 세대: " +  mainBoardRamGen + ", ram 세대: " + ramGen +" 주의: 견적에 저장된 CPU와 RAM의 정보가 사라집니다.");
                        rttr.addFlashAttribute("mbInfo", info);
                        rttr.addFlashAttribute("mbInfoName", infoName);
                        return "redirect:/PortablEstimate/mbParts";
                    }
                }
            }
            comEstimateVO.setMainBoard(info);
        }else if(infoName.equals("ram")) {
            if(comEstimateVO.getMainBoard() != null) { //메인보드와의 호환성 체크
                String mainBoardRamGen = mbService.getMbByMbName(comEstimateVO.getMainBoard()).getMbMemGen();
                String ramGen = ramService.getRamByRamName(info).getRamGen();

                if(mainBoardRamGen.equals(ramGen)) { //소켓 일치
                    model.addAttribute("actionNotice","성공적으로 RAM을 추가하였습니다.");
                } else {
                    if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                        comEstimateVO.setRam(info);
                        comEstimateVO.setMainBoard(null);
                        session.setAttribute("comEstimate", comEstimateVO);
                        return originalPage;
                    }
                    rttr.addFlashAttribute("actionNotice", "Ram세대와 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? 메인보드 ram 세대: " +  mainBoardRamGen + ", ram 세대: " + ramGen+" 주의: 견적에 저장된 메인보드의 정보가 사라집니다.");
                    rttr.addFlashAttribute("ramInfo", info);
                    rttr.addFlashAttribute("ramInfoName", infoName);
                    return "redirect:/PortablEstimate/ramParts";
                }
            }
            comEstimateVO.setRam(info);
        }else if(infoName.equals("storage")) {
            comEstimateVO.setStorage(info);
        }else if(infoName.equals("power")) {
            comEstimateVO.setPower(info);
        }else if(infoName.equals("gpu")) {
            comEstimateVO.setGpu(info);
        }else { // 들어온게 없을 때
            if(comEstimateVO == null) {
                comEstimateVO = new ComEstimateVO();
            }
            comEstimateVO.setUserID(session.getAttribute("userID").toString());
        }

        session.setAttribute("comEstimate", comEstimateVO);
        try {
            URI uri = new URI(originalPage);
            String path = uri.getPath();

            // path에서 첫 번째 슬래시를 제거하고, 첫 번째 슬래시부터 다음 슬래시까지의 부분 추출
            String[] pathSegments = path.split("/");
            originPage = pathSegments[1];
            originPage = "redirect:/" + originPage +"?isReOpen=true";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return originPage;
    }

    @RequestMapping("/cpuParts")
    public String cpuParts() {
        return "PortableEstimate/searchCpuParts";
    }

    @RequestMapping("/mbParts")
    public String mbParts() {
        return "PortableEstimate/searchMbParts";
    }

    @RequestMapping("/ramParts")
    public String ramParts() {
        return "PortableEstimate/searchRamParts";
    }

    @RequestMapping("/powerParts")
    public String powerParts() {
        return "PortableEstimate/searchPowerParts";
    }

    @RequestMapping("/storageParts")
    public String stoParts() {
        return "PortableEstimate/searchStorageParts";
    }

    @RequestMapping("/gpuParts")
    public String gpuParts() {
        return "PortableEstimate/searchGpuParts";
    }


}
