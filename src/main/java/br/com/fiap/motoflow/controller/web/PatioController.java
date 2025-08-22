package br.com.fiap.motoflow.controller.web;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patio")
public class PatioController {

    private final PatioService patioService;

    public PatioController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Dashboard do pátio")
    public String listarInfosPatio(@PathVariable Long id, Model model) {
        PatioQuantityResponse patioInfo = patioService.getPatioInfos(id);

        model.addAttribute("patioInfo", patioInfo);

        return "dashboard";
    }
}


