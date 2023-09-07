package com.lingfenglong.mydemo.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
//        String user = request.getParameter("user");
//        model.addAttribute("user", user);
        return "/index";
    }
}
