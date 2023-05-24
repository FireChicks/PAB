package com.kbd.PAB;

import com.kbd.PAB.Service.*;
import com.kbd.PAB.VO.BbsEstimateVO;
import com.kbd.PAB.VO.BbsVO;
import com.kbd.PAB.VO.ComEstimateVO;
import com.kbd.PAB.VO.UserVO;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping("/bbs")
public class BbsController {

    @Autowired
    private BbsService bbsService;
    @Autowired
    private BbsEstimateService bbsEstimateService;
    @Autowired
    private ComEstimateService comEstimateService;

    @Autowired
    private CpuService cpuService;

    @Autowired
    private MbService mbService;

    @Autowired
    private RamService ramService;


    @RequestMapping("")
    public String bbs(HttpSession session, Model model) {
        List<BbsVO> vos = bbsService.readAllBbs();
        model.addAttribute("bbsList", vos);
        return "bbs/bbsList";
    }

    @RequestMapping("/updateEstimate")
    public String updateEstimate(RedirectAttributes rttr, Model model, HttpSession session, @RequestParam(name = "info", defaultValue = "") String info, @RequestParam(name = "infoName", defaultValue = "none") String infoName, @RequestParam(name = "isForceAdd", defaultValue = "none") String isForceAdd) {

        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }

        ComEstimateVO comEstimateVO = (ComEstimateVO) session.getAttribute("writeEstimate");

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
                        session.setAttribute("writeEstimate", comEstimateVO);
                        return "bbs/bbsWrite";
                    }
                    rttr.addFlashAttribute("actionNotice", "cpu소켓과 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? CPU 소켓: " + cpuSocket + ", 메인보드 CPU 소켓: " + mainBoardSocket +" 주의: 견적에 저장된 메인보드의 정보가 사라집니다.");
                    rttr.addFlashAttribute("cpuInfo", info);
                    rttr.addFlashAttribute("cpuInfoName", infoName);
                    return "redirect:/bbs/cpuParts";
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
                        session.setAttribute("writeEstimate", comEstimateVO);
                        return "bbs/bbsWrite";
                    }
                    rttr.addFlashAttribute("actionNotice", "cpu소켓과 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? CPU 소켓: " + cpuSocket + ", 메인보드 CPU 소켓: " + mainBoardSocket+" 주의: 견적에 저장된 CPU와 RAM의 정보가 사라집니다.");
                    rttr.addFlashAttribute("mbInfo", info);
                    rttr.addFlashAttribute("mbInfoName", infoName);
                    return "redirect:/bbs/mbParts";
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
                            session.setAttribute("writeEstimate", comEstimateVO);
                            return "bbs/bbsWrite";
                        }
                        rttr.addFlashAttribute("actionNotice", "Ram세대와 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? 메인보드 ram 세대: " +  mainBoardRamGen + ", ram 세대: " + ramGen +" 주의: 견적에 저장된 CPU와 RAM의 정보가 사라집니다.");
                        rttr.addFlashAttribute("mbInfo", info);
                        rttr.addFlashAttribute("mbInfoName", infoName);
                        return "redirect:/bbs/mbParts";
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
                        session.setAttribute("writeEstimate", comEstimateVO);
                        return "bbs/bbsWrite";
                    }
                    rttr.addFlashAttribute("actionNotice", "Ram세대와 메인보드 소켓이 일치하지 않습니다. 그럼에도 추가하시겠습니까? 메인보드 ram 세대: " +  mainBoardRamGen + ", ram 세대: " + ramGen+" 주의: 견적에 저장된 메인보드의 정보가 사라집니다.");
                    rttr.addFlashAttribute("ramInfo", info);
                    rttr.addFlashAttribute("ramInfoName", infoName);
                    return "redirect:/bbs/ramParts";
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

        session.setAttribute("writeEstimate", comEstimateVO);
        return "bbs/bbsWrite";
    }

    @RequestMapping("/write")
    public String writeBbs(@RequestParam(name = "estimateID", defaultValue = "0") int estimateID, HttpSession session, Model model) {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }

        String userID = session.getAttribute("userID").toString();
        if(estimateID != 0) {
            if (!comEstimateService.isUserPosEstimate(estimateID, userID)) { //견적 소유 확인
                model.addAttribute("actionNotice", "당신이 소유하고 있는 견적이 아닙니다.");
                return "index";
            }

            ComEstimateVO comEstimateVO = comEstimateService.findByEstimateID(estimateID);
            session.setAttribute("writeEstimate", comEstimateVO);
            return "bbs/bbsWrite";
        } else {
            ComEstimateVO comEstimateVO = new ComEstimateVO();
            session.setAttribute("writeEstimate", comEstimateVO);
            return "bbs/bbsWrite";
        }
    }

    @RequestMapping("/update")
    public String updateBbs(@RequestParam(name = "bbsID") int bbsID, HttpSession session, Model model) {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }
        BbsVO bbsVO = bbsService.readByBbsID(bbsID);
        BbsEstimateVO comEstimateVO = bbsEstimateService.findByEstimateID(bbsVO.getBbsEstimateID());
        session.setAttribute("bbsVO", bbsVO);
        session.setAttribute("writeEstimate", comEstimateVO);
        return "bbs/bbsWrite";
    }

    @RequestMapping("/writeAction")
    public String writeBbsAction(@RequestParam(name = "bbsTitle") String bbsTitle,@RequestParam(name = "bbsContent") String bbsContent, HttpSession session, Model model) throws Exception {
        if(session.getAttribute("userID") == null) {
            model.addAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }
        if(bbsTitle == null || bbsTitle.equals("") || bbsContent == null || bbsContent.equals("")) {
            model.addAttribute("actionNotice","제목과 글을 작성해주시기 바랍니다.");
            return "bbs/bbsWrite";
        }
        ComEstimateVO cvo = (ComEstimateVO) session.getAttribute("writeEstimate");
        BbsEstimateVO Bvo = new BbsEstimateVO(cvo.getUserID(),cvo.getCpuName(),cvo.getMainBoard(), cvo.getRam(), cvo.getStorage(), cvo.getPower(), cvo.getGpu());
        Bvo.setComEstimateID(bbsEstimateService.findMaxBbs() + 1);
        if(bbsEstimateService.saveEstimate(Bvo) == 1) {
            BbsVO vo = new BbsVO(bbsTitle, bbsContent, Bvo.getComEstimateID());
            bbsService.writeBbs(vo);
            model.addAttribute("actionNotice","성공적으로 글을 저장했습니다.");
            return "redirect:/bbs";
        }
        model.addAttribute("actionNotice","저장에 실패했습니다.");
        return "bbs/bbsWrite";
    }

    @RequestMapping("/cpuParts")
    public String cpuParts() {
        return "bbs/Estimate/searchCpuParts";
    }

    @RequestMapping("/mbParts")
    public String mbParts() {
        return "bbs/Estimate/searchMbParts";
    }

    @RequestMapping("/ramParts")
    public String ramParts() {
        return "bbs/Estimate/searchRamParts";
    }

    @RequestMapping("/powerParts")
    public String powerParts() {
        return "bbs/Estimate/searchPowerParts";
    }

    @RequestMapping("/storageParts")
    public String stoParts() {
        return "bbs/Estimate/searchStorageParts";
    }

    @RequestMapping("/gpuParts")
    public String gpuParts() {
        return "bbs/Estimate/searchGpuParts";
    }





}

