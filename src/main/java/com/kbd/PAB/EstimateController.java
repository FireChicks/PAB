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

import java.util.List;

@Configuration
@ComponentScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })
@EntityScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })


@Controller
@RequestMapping("/estimate")
public class EstimateController {

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
                           @RequestParam(name = "isForceAdd", defaultValue = "none") String isForceAdd) {

        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }

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
                        return "Estimate/makeEstimate";
                    }
                    rttr.addFlashAttribute("actionNotice", "cpu소켓과 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? CPU 소켓: " + cpuSocket + ", 메인보드 CPU 소켓: " + mainBoardSocket +" 주의: 견적에 저장된 메인보드의 정보가 사라집니다.");
                    rttr.addFlashAttribute("cpuInfo", info);
                    rttr.addFlashAttribute("cpuInfoName", infoName);
                    return "redirect:/estimate/cpuParts";
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
                        return "Estimate/makeEstimate";
                    }
                    rttr.addFlashAttribute("actionNotice", "cpu소켓과 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? CPU 소켓: " + cpuSocket + ", 메인보드 CPU 소켓: " + mainBoardSocket+" 주의: 견적에 저장된 CPU와 RAM의 정보가 사라집니다.");
                    rttr.addFlashAttribute("mbInfo", info);
                    rttr.addFlashAttribute("mbInfoName", infoName);
                    return "redirect:/estimate/mbParts";
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
                            return "Estimate/makeEstimate";
                        }
                        rttr.addFlashAttribute("actionNotice", "Ram세대와 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? 메인보드 ram 세대: " +  mainBoardRamGen + ", ram 세대: " + ramGen +" 주의: 견적에 저장된 CPU와 RAM의 정보가 사라집니다.");
                        rttr.addFlashAttribute("mbInfo", info);
                        rttr.addFlashAttribute("mbInfoName", infoName);
                        return "redirect:/estimate/mbParts";
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
                        return "Estimate/makeEstimate";
                    }
                    rttr.addFlashAttribute("actionNotice", "Ram세대와 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? 메인보드 ram 세대: " +  mainBoardRamGen + ", ram 세대: " + ramGen+" 주의: 견적에 저장된 메인보드의 정보가 사라집니다.");
                    rttr.addFlashAttribute("ramInfo", info);
                    rttr.addFlashAttribute("ramInfoName", infoName);
                    return "redirect:/estimate/ramParts";
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
        return "Estimate/makeEstimate";
    }

    @RequestMapping("/resetEstimate")
    public String resetEstimate(HttpSession session, Model model) {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }
        ComEstimateVO vo = new ComEstimateVO();
        ComEstimateVO originalVO = ((ComEstimateVO) session.getAttribute("comEstimate"));
        vo.setUserID(originalVO.getUserID());

        if(session.getAttribute("comEstimate") != null) {
            if(originalVO != null || !originalVO.equals("")) {
                vo.setComEstimateID(originalVO.getComEstimateID());
            }
        }
        session.setAttribute("comEstimate", vo);
        return "Estimate/makeEstimate";
    }


    @RequestMapping("/edit")
    public String editAction(HttpSession session,Model model, @RequestParam(name = "estimateID", defaultValue = "") int estimateID) {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }

        String userID = session.getAttribute("userID").toString();
        if(!comEstimateService.isUserPosEstimate(estimateID, userID)) { //견적 소유 확인
            model.addAttribute("actionNotice","당신이 소유하고 있는 견적이 아닙니다.");
            return "index";
        }

        ComEstimateVO comEstimateVO = comEstimateService.findByEstimateID(estimateID);
        session.setAttribute("comEstimate", comEstimateVO);
        return "Estimate/makeEstimate";
    }

    @RequestMapping("/deleteAction")
    public String deleteAction(HttpSession session, Model model, @RequestParam(name = "estimateID", defaultValue = "") int estimateID) {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }
        String userID = session.getAttribute("userID").toString();
        if(comEstimateService.deleteEstimate(estimateID, userID) == 1) {
            model.addAttribute("actionNotice","성공적으로 견적을 삭제했습니다.");
            List<ComEstimateVO> vos = comEstimateService.findByUserID(userID);
            model.addAttribute("estimateList", vos);
            return "myEstimate";
        } else {
            model.addAttribute("actionNotice","견적과 계정이 일치하지 않습니다.");
            return  "index";
        }
    }

    @RequestMapping("/saveAction")
    public String saveAction(HttpSession session, Model model) throws Exception {
        ComEstimateVO comEstimateVO = (ComEstimateVO) session.getAttribute("comEstimate");
        int isSaved = comEstimateService.saveEstimate(comEstimateVO);
        if(isSaved == 1) {
            String userID = session.getAttribute("userID").toString();
            List<ComEstimateVO> vos = comEstimateService.findByUserID(userID);
            model.addAttribute("estimateList", vos);                   //내 견적에서 조회할 견적들

            model.addAttribute("comEstimate", null);       //세션에 저장된 견적 초기화
            model.addAttribute("actionNotice","성공적으로 견적을 저장했습니다.");
            return "myEstimate";
        } else if(isSaved == 2){
            String userID = session.getAttribute("userID").toString();
            List<ComEstimateVO> vos = comEstimateService.findByUserID(userID);
            model.addAttribute("estimateList", vos);
            model.addAttribute("comEstimate", null);       //세션에 저장된 견적 초기화
            model.addAttribute("actionNotice","성공적으로 견적을 업데이트했습니다.");
            return "myEstimate";
        }else {
            model.addAttribute("actionNotice","계정당 저장 가능한 견적의 개수는 3개입니다.");
            return "Estimate/makeEstimate";
        }
    }

    @RequestMapping("/myEstimate")
    public String getMyEstimate(Model model, HttpSession session) {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
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

    @GetMapping("/getEstimate")
        public ComEstimateVO getSessionEstimateValue(HttpSession session) {
            if(session.getAttribute("comEstimate") != null) {
                ComEstimateVO vo = (ComEstimateVO) session.getAttribute("comEstimate");
                return  vo;
            }
            return null;
    }
}
