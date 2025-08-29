package br.com.fiap.motoflow.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class LoginWebController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}


