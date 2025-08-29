package br.com.fiap.motoflow.controller.web;

import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.repository.PatioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web")
public class IndexController {

    private final PatioRepository patioRepository;

    public IndexController(PatioRepository patioRepository) {
        this.patioRepository = patioRepository;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Patio> patios = patioRepository.findAll();
        model.addAttribute("patios", patios);
        return "index";
    }

    @GetMapping("app/patio/{id}")
    public String app(@PathVariable Long id, Model model) {
        model.addAttribute("patioId", id);
        return "app";
    }
}
