package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.CadastroPosicaoDto;
import br.com.fiap.motoflow.dto.responses.CadastroPosicaoResponseDto;
import br.com.fiap.motoflow.dto.responses.MotoHorizontalDto;
import br.com.fiap.motoflow.dto.responses.PosicoesHorizontaisDto;
import br.com.fiap.motoflow.service.PosicaoPatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/posicoes")
@SecurityRequirement(name = "bearerAuth")
public class PosicaoPatioController {

    private final PosicaoPatioService posicaoPatioService;

    public PosicaoPatioController(PosicaoPatioService posicaoPatioService) {
        this.posicaoPatioService = posicaoPatioService;
    }

    @Operation(summary = "Cadastrar posições de um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posições cadastradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado")
    })
    @PostMapping
    public ResponseEntity<CadastroPosicaoResponseDto> cadastrarPosicao(@RequestBody @Valid CadastroPosicaoDto dto) {

        final CadastroPosicaoResponseDto response = posicaoPatioService.cadastrarPosicao(dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Pegar todas as posições horizontais de um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posições horizontais retornadas"),
            @ApiResponse(responseCode = "404", description = "Pátio não encontrado")
    })
    @GetMapping("/{patioId}")
    public ResponseEntity<List<PosicoesHorizontaisDto>> getPosicoesHorizontais(@PathVariable Long patioId) {
        return ResponseEntity.ok(posicaoPatioService.posicoesHorizontais(patioId));
    }

    @Operation(summary = "Pegar todas as motos de uma posição horizontal de um pátio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motos retornadas"),
            @ApiResponse(responseCode = "404", description = "Pátio ou posição não encontrado")
    })
    @GetMapping("/{patioId}/{posicaoHorizontal}")
    public ResponseEntity<MotoHorizontalDto> getMotosPorPosicaoHorizontal(@PathVariable Long patioId, @PathVariable String posicaoHorizontal) {

        MotoHorizontalDto motos = posicaoPatioService.motosPorPosicaoHorizontal(patioId, posicaoHorizontal);
        return ResponseEntity.ok(motos);
    }
}
