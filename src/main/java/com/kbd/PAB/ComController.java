package com.kbd.PAB;


import com.kbd.PAB.Nor.Searchable;
import com.kbd.PAB.Service.ComService;
import com.kbd.PAB.Service.CpuService;
import com.kbd.PAB.VO.ComVO;
import com.kbd.PAB.VO.CpuVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class ComController {

    @Autowired
    private ComService comService;

    @RequestMapping("/read")
    public List<ComVO> getComments(@RequestParam(name="bbsID")int bbsID) {
        return comService.readByBbsID(bbsID);
    }

    @RequestMapping("/writeComment")
    public boolean writeComment(HttpSession session, @RequestParam(name = "comContent") String comComment,
                               @RequestParam(name="bbsID") int bbsID,
                               @RequestParam(name = "opponentWriter") String opponentWriter){
        if(session.getAttribute("userID") == null) {
            return false;
        }

        ComVO comVO = new ComVO(bbsID, comComment, session.getAttribute("userID").toString(), opponentWriter);
        comService.writeComment(comVO);
        return true;

    }
}
