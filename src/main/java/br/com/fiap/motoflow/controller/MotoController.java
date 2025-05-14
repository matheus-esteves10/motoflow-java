package br.com.fiap.motoflow.controller;

import br.com.fiap.motoflow.dto.responses.AlocarMotoDto;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping("/posicao")
    @Operation(
            summary = "Buscar posição da moto",
            description = "Busca detalhes da moto pela placa.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto encontrada no patio", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada", content = @Content)
            }
    )

    @PostMapping
    public ResponseEntity<PosicaoMotoResponse> buscarPosicaoPorPlaca(@RequestParam String placa) {
        PosicaoMotoResponse response = motoService.buscarPosicaoPorPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{placa}")
    @Operation(summary = "Excluir moto por placa", description = "Exclui a moto pela placa. Remove o vínculo com a posição se não estiver alugada.")
    public ResponseEntity<Void> excluirMotoPorPlaca(@PathVariable String placa) {
        motoService.excluirMotoPorPlaca(placa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/alocar")
    public ResponseEntity<Void> alocarMoto(@RequestBody AlocarMotoDto dto) {
        motoService.alocarMotoNaPosicao(dto.placa(), dto.posicaoHorizontal(), dto.posicaoVertical());
        return ResponseEntity.ok().build();
    }
}