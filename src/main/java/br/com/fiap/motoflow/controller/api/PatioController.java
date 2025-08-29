package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.dto.responses.PatioResponse;
import br.com.fiap.motoflow.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @Operation(summary = "Listar todos os patios" )
    public ResponseEntity<List<PatioResponse>> listarTodosPatios() {
        return ResponseEntity.ok(patioService.getAllPatios());
    }
}
