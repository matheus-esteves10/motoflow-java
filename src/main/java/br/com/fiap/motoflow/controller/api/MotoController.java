package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.CadastroMotoComPatioDto;
import br.com.fiap.motoflow.dto.MotoDto;
import br.com.fiap.motoflow.dto.responses.AlocarMotoDto;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.dto.responses.ResponsePosicao;
import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/motos")
@SecurityRequirement(name = "bearerAuth")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @Operation(summary = "Cadastrar uma nova moto", description = "Salva uma nova moto no sistema")
    @PostMapping
    public ResponseEntity<Moto> salvarMoto(@RequestBody MotoDto dto, @AuthenticationPrincipal Operador operador) {
        Moto novaMoto = motoService.save(dto);
        return ResponseEntity.ok(novaMoto);
    }

    @GetMapping("/posicao")
    @Operation(
            summary = "Buscar posição da moto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto encontrada no patio", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada", content = @Content)
            }
    )
    public ResponseEntity<PosicaoMotoResponse> buscarPosicaoPorPlaca(@RequestParam String placa, @AuthenticationPrincipal Operador operador) {

        PosicaoMotoResponse response = motoService.buscarPosicaoPorPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{placa}")
    @Operation(summary = "Excluir moto por placa", description = "Exclui a moto pela placa. Remove o vínculo com a posição se não estiver alugada.")
    public ResponseEntity<Void> excluirMotoPorPlaca(@PathVariable String placa, @AuthenticationPrincipal Operador operador) {

        motoService.excluirMotoPorPlaca(placa);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/alocacao")
    @Operation(summary = "Alocar moto na posição",
            description = "Esse método funciona para uma moto já existente no sistema ser alocada em uma posição especifica do patio.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posição alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto ou Posição nao encontrada", content = @Content)
            }
    )
    public ResponseEntity<ResponsePosicao> alocarMoto(@RequestBody AlocarMotoDto dto, @AuthenticationPrincipal Operador operador) {

        ResponsePosicao response = motoService.alocarMotoNaPosicao(dto.placa(), dto.posicaoHorizontal(), dto.posicaoVertical());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastro-e-alocacao")
    @Operation(
            summary = "Cadastrar nova moto e alocar automaticamente",
            description = "Cadastra uma nova moto e aloca na primeira posição livre no pátio informado, seguindo ordem A1, A2, B1...",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Moto cadastrada e alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto ou patio nao encontrado", content = @Content)
            }
    )
    public ResponseEntity<ResponsePosicao> cadastrarEAlocar(@RequestBody CadastroMotoComPatioDto dto, @AuthenticationPrincipal Operador operador) {

        MotoDto motoDto = new MotoDto(
                dto.getTipoMoto(),
                dto.getAno(),
                dto.getPlaca(),
                dto.getPrecoAluguel(),
                dto.getStatusMoto(),
                dto.getDataAlocacao()
        );

        ResponsePosicao response = motoService.cadastrarMotoEAlocar(motoDto, dto.getIdPatio());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{placa}/aluguel")
    @Operation(summary = "Atualiza status de aluguel da moto e remove do pátio se alugada",
    responses = {
        @ApiResponse(responseCode = "200", description = "Status de aluguel atualizado com sucesso", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Moto nao encontrada", content = @Content)
    }
    )
    public ResponseEntity<MotoDto> atualizarStatusAluguel(@PathVariable String placa, @RequestParam StatusMoto statusMoto,
                                                       @AuthenticationPrincipal Operador operador) {

        Moto motoAtualizada = motoService.atualizarStatusAluguel(placa, statusMoto);

        MotoDto motoDto = new MotoDto(
                motoAtualizada.getTipoMoto(),
                motoAtualizada.getAno(),
                motoAtualizada.getPlaca(),
                motoAtualizada.getPrecoAluguel(),
                motoAtualizada.getStatusMoto(),
                motoAtualizada.getDataAluguel()
        );

        return ResponseEntity.ok(motoDto);
    }

    @Operation(
            summary = "Alocar moto existente em posição livre",
            description = "Aloca uma moto já cadastrada no sistema em uma posição livre e sequencial no pátio especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto ou posição não encontrada", content = @Content)
            }
    )
    @PostMapping("/{placa}/alocacao/{idPatio}")
    public ResponseEntity<ResponsePosicao> alocarMotoExistente(
            @PathVariable String placa, @PathVariable Long idPatio, @AuthenticationPrincipal Operador operador) {

        ResponsePosicao response = motoService.alocarMotoExistente(placa, idPatio);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

