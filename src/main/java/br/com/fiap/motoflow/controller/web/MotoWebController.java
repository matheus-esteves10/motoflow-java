package br.com.fiap.motoflow.controller.web;

import br.com.fiap.motoflow.service.MotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/motos")
public class MotoWebController {

    private final MotoService motoService;

    public MotoWebController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping("/patio/{id}")
    public String index(@PathVariable Long id, Model model, Pageable pageable) {
        var motos = motoService.findAllByPatioId(id, pageable);
        model.addAttribute("motos", motos);
        model.addAttribute("patioId", id);
        return "motos";
    }
}

