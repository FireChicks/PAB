package com.kbd.PAB;

import com.kbd.PAB.Service.UserService;
import com.kbd.PAB.VO.ComEstimateVO;
import com.kbd.PAB.VO.UserVO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/loginAction")
    public String loginAction(@RequestParam(name = "userID")String userID, @RequestParam(name = "userPW")String userPW, HttpSession session, Model model) {
        int isLoginSuccess = userService.login(userID, userPW);
        if(isLoginSuccess == 1) {
            session.setAttribute("userID", userID);
            ComEstimateVO comEstimateVO = new ComEstimateVO();
            comEstimateVO.setUserID(session.getAttribute("userID").toString());
            session.setAttribute("comEstimate", comEstimateVO);
            return "redirect:/index";
        } else if (isLoginSuccess == 0) {
            model.addAttribute("actionNotice","ID나 PW가 불일치합니다.");
            return "/login";
        } else {
            model.addAttribute("actionNotice","예상하지 못한 오류가 발생했습니다. 다시 시도해주십시오.");
            return "/login";
        }

    }

    @RequestMapping("/logoutAction")
    public String logOutAction(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @RequestMapping("/joinAction")
    public String joinAction(Model model, RedirectAttributes rttr, @RequestParam(name = "userID")String userID,
                             @RequestParam(name = "userPW")String userPW,
                             @RequestParam(name = "userName")String userName,
                             @RequestParam(name = "file") MultipartFile file) throws IOException {
    int isValidID   = userService.isValidID(userID);
    int isValidName = userService.isValidUserName(userName);
    byte[] bytes = IOUtils.toByteArray(file.getInputStream());


    if(isValidID == 1 && isValidName == 1) {
        UserVO userVO = new UserVO(userID, userPW, userName, 0,bytes,0);
        userService.join(userVO);
        return "index";
    } else if(isValidID == 0) {
        model.addAttribute("actionNotice","ID가 유효하지 않습니다.");
        return "/join";
    }else if(isValidName == 0) {
        model.addAttribute("actionNotice","name이 유효하지 않습니다.");
        return "/join";
    }
        rttr.addAttribute("actionNotice","성공적으로 회원가입 완료했습니다.");
    return "redirect:/index";
    }

    @RequestMapping("/myPage")
    public String getUsers(Model model, HttpSession session) {
        if(session.getAttribute("userID") == null) {
            return "index";
        }
        String userID = session.getAttribute("userID").toString();
        UserVO userVO = userService.findUser(userID);
        String encodedString = Base64.getEncoder().encodeToString(userVO.getUserProfile());
        model.addAttribute("user", userVO);
        model.addAttribute("userProfile", encodedString);
        return "myPage";
    }

    @RequestMapping(value = "/user/getUserProfile", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String encodeUserProfile(@RequestParam(name = "userID")String userID) {
        String encodeString =  Base64.getEncoder().encodeToString(userService.getUserProfile(userID));
        return encodeString;
    }

    @RequestMapping("/imgCngAction")
    public String profileChangeAction(Model model, HttpSession session, @RequestParam(name = "file") MultipartFile file) throws IOException{
        if(session.getAttribute("userID") == null) {
            return "index";
        }
        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        String userID = session.getAttribute("userID").toString();
        UserVO userVO = userService.findUser(userID);
        userVO.setUserProfile(bytes);

        userService.join(userVO);

        String encodedString = Base64.getEncoder().encodeToString(userVO.getUserProfile());
        model.addAttribute("user", userVO);
        model.addAttribute("userProfile", encodedString);
        model.addAttribute("actionNotice","성공적으로 프로필 이미지를 수정 완료했습니다.");
        return "/myPage";
    }

}

