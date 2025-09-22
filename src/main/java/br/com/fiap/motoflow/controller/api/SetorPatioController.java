package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.CriarSetorDto;
import br.com.fiap.motoflow.dto.responses.SetoresDto;
import br.com.fiap.motoflow.dto.responses.SetorDto;
import br.com.fiap.motoflow.service.SetorPatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posicoes")
@SecurityRequirement(name = "bearerAuth")
public class SetorPatioController {

    private final SetorPatioService setorPatioService;

    public SetorPatioController(SetorPatioService setorPatioService) {
        this.setorPatioService = setorPatioService;
    }

    @Operation(summary = "Cadastrar novo setor em um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Setor cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado"),
            @ApiResponse(responseCode = "40", description = "Limite de posições atingidas")
    })
    @PostMapping("/{patioId}")
    public ResponseEntity<SetorDto> cadastrarSetor(@RequestBody @Valid CriarSetorDto dto, @PathVariable Long patioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(setorPatioService.criarSetorNoPatio(patioId, dto));
    }

    @Operation(summary = "Pegar todos os setores de um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posições horizontais retornadas"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado")
    })
    @GetMapping("/{patioId}")
    public ResponseEntity<List<SetoresDto>> getSetores(@PathVariable Long patioId) {
        return ResponseEntity.ok(setorPatioService.getSetores(patioId));
    }

    @Operation(summary = "Pegar todas as motos de um setor de um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motos retornadas"),
            @ApiResponse(responseCode = "404", description = "Pátio ou posição não encontrado")
    })
    @GetMapping("/{patioId}/{setor}")
    public ResponseEntity<SetorDto> getMotosPorSetor(@PathVariable Long patioId, @PathVariable String setor) {
        return ResponseEntity.ok(setorPatioService.motosPorSetor(patioId, setor));
    }

    @Operation(summary = "Deletar um setor de um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Setor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pátio ou setor não encontrado")
    })
    @DeleteMapping("/{setor}/{patioId}")
    public ResponseEntity<Void> deletarSetor(@PathVariable String setor, @PathVariable Long patioId) {
        setorPatioService.deletarSetor(setor, patioId);
        return ResponseEntity.noContent().build();
    }
}
