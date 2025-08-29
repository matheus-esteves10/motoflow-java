package br.com.fiap.motoflow.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/")
    public String redirectToWeb() {
        return "redirect:/web";
    }
}

