package br.com.fiap.motoflow.controller.web;

import br.com.fiap.motoflow.dto.web.PatioDashboardResponse;
import br.com.fiap.motoflow.service.PatioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/patio")
public class PatioWebController {

    private final PatioService patioService;

    public PatioWebController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping("/{id}")
    public String dashboard(@PathVariable Long id, Model model) {
        PatioDashboardResponse patioInfo = patioService.getDashboardInfos(id);
        model.addAttribute("patioInfo", patioInfo);
        model.addAttribute("patioId", id);
        return "dashboard"; // dashboard.html
    }
}
