package br.com.fiap.motoflow.controller;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patio")
public class PatioController {

    private final PatioService patioService;

    public PatioController(PatioService patioService) {
        this.patioService = patioService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar informações do patio")
    public ResponseEntity<PatioQuantityResponse> listarInfosPatio(@PathVariable Long id) {

        return ResponseEntity.ok(patioService.getPatioInfos(id));
    }
}
