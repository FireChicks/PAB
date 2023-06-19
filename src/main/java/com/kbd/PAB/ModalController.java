package com.kbd.PAB;

import com.kbd.PAB.Nor.PageInfo;
import com.kbd.PAB.Nor.Searchable;
import com.kbd.PAB.Service.*;
import com.kbd.PAB.VO.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/modal")
public class ModalController {

    @Autowired
    private BbsService bbsService;
    @Autowired
    private BbsEstimateService bbsEstimateService;
    @Autowired
    private ComEstimateService comEstimateService;

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

    @RequestMapping("/cpuView")
    public String cpuView(Model model,@RequestParam(name="cpuID")int cpuID){
        return "modal/partsView/cpuView";
    }

    @RequestMapping("/bbsEstimate")
    public String getModalBbsEstimate(Model model, @RequestParam(name="estimateID")int estimateID) {
        BbsEstimateVO vo = bbsEstimateService.findByEstimateID(estimateID);
        model.addAttribute("bbsEstimate", vo);
        return "modal/estimate/modalBbsEstimate";
    }

    @RequestMapping("/addParts")
    public String estimate(HttpServletRequest request, RedirectAttributes rttr, Model model, HttpSession session,
                           @RequestParam(name = "info", defaultValue = "") String info,
                           @RequestParam(name = "infoName", defaultValue = "none") String infoName,
                           @RequestParam(name = "isForceAdd", defaultValue = "none") String isForceAdd) {

        if(session.getAttribute("userID") == null) {
            rttr.addFlashAttribute("actionNotice","먼저 로그인해주시기 바랍니다.");
            return "index";
        }
        String referer = request.getHeader("Referer"); // 이전 페이지 정보

        ComEstimateVO comEstimateVO = (ComEstimateVO) session.getAttribute("comEstimate");

        if(infoName.equals("cpuName")) {
            if(comEstimateVO.getMainBoard() != null) { //메인보드와의 호환성 체크
                String mainBoardSocket = mbService.getMbByMbName(comEstimateVO.getMainBoard()).getMb_cpu_socket();
                String cpuSocket = cpuService.getCpuVOByCpuName(info).getCpu_socket();

                if(mainBoardSocket.equals(cpuSocket)) { //소켓 일치
                    rttr.addFlashAttribute("actionNotice","성공적으로 CPU를 추가하였습니다.");
                } else {
                    if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                        comEstimateVO.setCpuName(info);
                        comEstimateVO.setMainBoard(null);
                        session.setAttribute("comEstimate", comEstimateVO);
                        return "redirect:" + referer;
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
                    rttr.addFlashAttribute("actionNotice","성공적으로 메인보드를 추가하였습니다.");
                } else {
                    if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                        comEstimateVO.setCpuName(null);
                        comEstimateVO.setRam(null);
                        comEstimateVO.setMainBoard(info);
                        session.setAttribute("comEstimate", comEstimateVO);
                        return "redirect:" + referer;
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
                        rttr.addFlashAttribute("actionNotice","성공적으로 메인보드를 추가하였습니다.");
                    } else {
                        if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                            comEstimateVO.setCpuName(null);
                            comEstimateVO.setRam(null);
                            comEstimateVO.setMainBoard(info);
                            session.setAttribute("comEstimate", comEstimateVO);
                             return "redirect:" + referer;
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
                    rttr.addFlashAttribute("actionNotice","성공적으로 RAM을 추가하였습니다.");
                } else {
                    if(!isForceAdd.equals("none")) { //소켓이 틀려도 추가할 시
                        comEstimateVO.setRam(info);
                        comEstimateVO.setMainBoard(null);
                        session.setAttribute("comEstimate", comEstimateVO);
                         return "redirect:" + referer;
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
        rttr.addFlashAttribute("actionNotice", "성공적으로 추가가 완료되었습니다.");
         return "redirect:" + referer;
    }

    @PostMapping("/addEstimate")
    @ResponseStatus(HttpStatus.OK)
    public void processEstimate(HttpSession session, @RequestParam String cpuName, @RequestParam String mbName, @RequestParam String ramName, @RequestParam String stoName, @RequestParam String powName, @RequestParam String gpuName) {
        ComEstimateVO vo = new ComEstimateVO();
        if (cpuName != null && !cpuName.equals("")) {
            vo.setCpuName(cpuName);
        }
        if (mbName != null && !mbName.equals("")) {
            vo.setMainBoard(mbName);
        }
        if (ramName != null && !ramName.equals("")) {
            vo.setRam(ramName);
        }
        if (stoName != null && !stoName.equals("")) {
            vo.setStorage(stoName);
        }
        if (powName != null && !powName.equals("")) {
            vo.setPower(powName);
        }
        if (gpuName != null && !gpuName.equals("")) {
            vo.setGpu(gpuName);
        }

        session.setAttribute("comEstimate", vo);
    }

    @RequestMapping("/addComByPart")
    public String writeComByPart(@RequestParam("partID") int partID,
                                 @RequestParam("partCategory") String partCategory,
                                 @RequestParam("partName") String partName,
                                 @RequestParam("partImgUrl") String partImgUrl,
                                 @RequestParam("bbsID") String bbsID,
                                 Model model) {
        PartsVO partsVO = new PartsVO(partID, partCategory, partName, partImgUrl);

        model.addAttribute("partsVO", partsVO);
        model.addAttribute("bbsID", bbsID);
        return "bbs/com/writeByPart";
    }

    @RequestMapping("/allPartView")
    public String getAllPart(Model model, int bbsID){
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

        return "modal/partsView/allView";
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

