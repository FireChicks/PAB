package com.kbd.PAB;

import com.kbd.PAB.Service.UserService;
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


import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/loginAction")
    public String loginAction(@RequestParam(name = "userID")String userID, @RequestParam(name = "userPW")String userPW, HttpSession session) {
        int isLoginSuccess = userService.login(userID, userPW);
        if(isLoginSuccess == 1) {
            session.setAttribute("userID", userID);
            return "index";
        } else if (isLoginSuccess == 0) {
            return "redirect:/login";
        } else {
            return "redirect:/login";
        }

    }

    @RequestMapping("/logoutAction")
    public String logOutAction(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @RequestMapping("/joinAction")
    public String joinAction(@RequestParam(name = "userID")String userID,
                             @RequestParam(name = "userPW")String userPW,
                             @RequestParam(name = "userName")String userName,
                             @RequestParam(name = "file") MultipartFile file        ) throws IOException {
    int isValidID   = userService.isValidID(userID);
    int isValidName = userService.isValidUserName(userName);
    byte[] bytes = IOUtils.toByteArray(file.getInputStream());


    if(isValidID == 1 && isValidName == 1) {
        UserVO userVO = new UserVO(userID, userPW, userName, 0,bytes,0);
        userService.join(userVO);
        return "index";
    } else if(isValidID == 0) {

    }else if(isValidName == 0) {

    }
    return "index";
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





}

