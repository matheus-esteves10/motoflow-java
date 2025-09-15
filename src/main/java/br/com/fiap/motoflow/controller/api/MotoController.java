package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.*;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.dto.responses.ResponseMovimentacao;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/motos")
@SecurityRequirement(name = "bearerAuth")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @PostMapping("/{idPatio}")
    @Operation(
            summary = "Cadastrar nova moto e alocar setor",
            description = "Cadastra uma nova moto e aloca no setor informado na reaquisição",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Moto cadastrada e alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Setor Cheio", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Patio não encontrado", content = @Content)
            }
    )
    public ResponseEntity<ResponseMovimentacao> alocarMoto(@Valid @RequestBody CadastroMotoDto dto,
                                                           @PathVariable Long idPatio, @AuthenticationPrincipal Operador operador) {
        return new ResponseEntity<>(motoService.alocarMoto(dto, idPatio), HttpStatus.CREATED);
    }

    @GetMapping("/posicao")
    @Operation(
            summary = "Buscar posição da moto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto encontrada no patio", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada", content = @Content),
                    @ApiResponse(responseCode = "40", description = "Moto não alocada em nenhum setor", content = @Content)
            }
    )
    public ResponseEntity<PosicaoMotoResponse> buscarPosicaoPorPlaca(@RequestParam String placa, @AuthenticationPrincipal Operador operador) {
        return ResponseEntity.ok(motoService.buscarSetorPorPlaca(placa));
    }

    @PutMapping("/alocacao/{idPatio}")
    @Operation(
            summary = "Alterar setor da moto",
            description = "Altera o setor da moto, caso a moto esteja disponível",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Setor alterado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Moto Indisponível", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Moto, Setor ou Pátio não encontrados", content = @Content)
            }
    )
    public ResponseEntity<ResponseMovimentacao> alterarSetorMoto(@Valid @RequestBody SetorMotoDto dto,
                                                                 @PathVariable Long idPatio, @AuthenticationPrincipal Operador operador) {
        return ResponseEntity.ok(motoService.alterarSetor(dto, idPatio));
    }

    @PatchMapping("/{placa}")
    @Operation(summary = "Atualiza status da moto e remove da posicão se necessário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status da moto atualizada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto nao encontrada", content = @Content)
            }
    )
    public ResponseEntity<ResponseMovimentacao> alterarStatusMoto(@PathVariable String placa, @Valid @RequestBody EditarStatusMotoDto dto,
                                                                 @AuthenticationPrincipal Operador operador) {
        return ResponseEntity.ok(motoService.alterarStatusMoto(dto, placa));
    }

    @DeleteMapping("/{placa}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover moto do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Moto removida com sucesso", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Moto nao encontrada", content = @Content)
            }
    )
    public ResponseEntity<Void> removerMoto(@PathVariable String placa, @AuthenticationPrincipal Operador operador) {
        motoService.deletarMoto(placa);
        return ResponseEntity.noContent().build();
    }


}
