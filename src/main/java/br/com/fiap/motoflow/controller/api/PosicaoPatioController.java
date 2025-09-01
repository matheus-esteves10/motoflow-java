package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.CadastroPosicaoDto;
import br.com.fiap.motoflow.dto.responses.CadastroPosicaoResponseDto;
import br.com.fiap.motoflow.service.PosicaoPatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posicoes")
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
}
