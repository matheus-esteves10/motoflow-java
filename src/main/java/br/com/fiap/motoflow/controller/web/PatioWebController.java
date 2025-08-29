package br.com.fiap.motoflow.controller.web;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.service.PatioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/patio")
public class PatioWebController {

    private final PatioService patioService;

    public PatioWebController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping("/{id}")
    public String dashboard(@PathVariable Long id, Model model) {
        PatioQuantityResponse patioInfo = patioService.getPatioInfos(id);
        model.addAttribute("patioInfo", patioInfo);
        model.addAttribute("patioId", id);
        return "dashboard"; // dashboard.html
    }

    @GetMapping("/{id}/stats")
    @ResponseBody
    public PatioQuantityResponse getPatioStats(@PathVariable Long id) {
        return patioService.getPatioInfos(id);
    }
}



