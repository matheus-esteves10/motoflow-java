package br.com.fiap.motoflow.controller.web;

import br.com.fiap.motoflow.dto.OperadorDto;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.service.OperadorService;
import br.com.fiap.motoflow.service.PatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class OperadorWebController {

    private final OperadorService operadorService;
    private final PatioService patioService;

    @Autowired
    public OperadorWebController(OperadorService operadorService, PatioService patioService) {
        this.operadorService = operadorService;
        this.patioService = patioService;
    }

    @GetMapping("/{idPatio}")
    public String listarUsuarios(@PathVariable Long idPatio, Model model, Pageable pageable) {
        Page<Operador> operadores = operadorService.listarOperadoresPorPatio(idPatio, pageable);
        Patio patio = patioService.getPatioById(idPatio);

        model.addAttribute("operadores", operadores);
        model.addAttribute("patio", patio);
        model.addAttribute("patioId", idPatio);
        return "usuarios";
    }

    @PostMapping("/{idPatio}")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = {"operadores", "operadoresPorPatioWeb"}, allEntries = true)
    public String cadastrarUsuario(@PathVariable Long idPatio, @RequestParam String nome, @RequestParam String senha) {
        operadorService.salvarOperador(new OperadorDto(nome, senha, idPatio));
        return "redirect:/usuarios/" + idPatio;
    }

    @DeleteMapping("/{idPatio}/{idOperador}")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = {"operadores", "operadoresPorPatioWeb"}, allEntries = true)
    public String deletarUsuario(@PathVariable Long idPatio, @PathVariable Long idOperador) {
        operadorService.excluirOperador(idOperador);
        return "redirect:/usuarios/" + idPatio;
    }
}
