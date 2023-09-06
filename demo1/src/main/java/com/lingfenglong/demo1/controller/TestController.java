package com.lingfenglong.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello from security";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "authorities/admin";
    }
    @GetMapping("/manager")
    public String manager() {
        return "authorities/manager";
    }
    @GetMapping("/user")
    public String user() {
        return "authorities/user";
    }

    @GetMapping("/salesman")
    public String salesman() {
        return "roles/salesman";
    }

    @GetMapping("/developer")
    public String developer() {
        return "roles/developer";
    }
}
