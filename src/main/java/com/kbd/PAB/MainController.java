package com.kbd.PAB;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping("/test")
    public String home(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, Model model) {
        int MaxPageNum = 10; // 나중에 DB에서 받아오는걸로 처리

        model.addAttribute("pageNum", pageNum);
        model.addAttribute("maxPageNum", MaxPageNum);

        return "test";
    }

    @RequestMapping("/test2")
    public String test2(Model model) {
        return "test2";
    }
}
