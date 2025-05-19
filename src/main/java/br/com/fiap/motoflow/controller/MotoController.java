package br.com.fiap.motoflow.controller;

import br.com.fiap.motoflow.dto.CadastroMotoComPatioDto;
import br.com.fiap.motoflow.dto.MotoDto;
import br.com.fiap.motoflow.dto.responses.AlocarMotoDto;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.dto.responses.ResponsePosicao;
import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @Operation(summary = "Cadastrar uma nova moto", description = "Salva uma nova moto no sistema")
    @PostMapping
    public ResponseEntity<Moto> salvarMoto(@RequestBody MotoDto dto) {
        Moto novaMoto = motoService.save(dto);
        return ResponseEntity.ok(novaMoto);
    }

    @GetMapping
    @Operation(summary = "Listar todas as motos",
            description = "Retorna uma lista paginada de motos cadastradas")
    public Page<Moto> listarMotos(Pageable pageable) {
        return motoService.findAll(pageable);
    }

    @GetMapping("/posicao")
    @Operation(
            summary = "Buscar posição da moto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto encontrada no patio", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada", content = @Content)
            }
    )
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
    @Operation(summary = "Alocar moto na posição",
            description = "Esse método funciona para uma moto já existente no sistema ser alocada em uma posição especifica do patio.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posição alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto ou Posição nao encontrada", content = @Content)
            }
    )
    public ResponseEntity<ResponsePosicao> alocarMoto(@RequestBody AlocarMotoDto dto) {
        ResponsePosicao response = motoService.alocarMotoNaPosicao(dto.placa(), dto.posicaoHorizontal(), dto.posicaoVertical());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar-e-alocar")
    @Operation(
            summary = "Cadastrar nova moto e alocar automaticamente",
            description = "Cadastra uma nova moto e aloca na primeira posição livre no pátio informado, seguindo ordem A1, A2, B1...",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Moto cadastrada e alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto ou patio nao encontrado", content = @Content)
            }
    )
    public ResponseEntity<ResponsePosicao> cadastrarEAlocar(@RequestBody CadastroMotoComPatioDto dto) {
        MotoDto motoDto = new MotoDto(
                dto.tipoMoto(),
                dto.ano(),
                dto.placa(),
                dto.precoAluguel(),
                dto.isAlugada(),
                dto.dataAlocacao()
        );

        ResponsePosicao response = motoService.cadastrarMotoEAlocar(motoDto, dto.idPatio());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{placa}/aluguel")
    @Operation(summary = "Atualiza status de aluguel da moto e remove do pátio se alugada",
    responses = {
        @ApiResponse(responseCode = "200", description = "Status de aluguel atualizado com sucesso", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Moto nao encontrada", content = @Content)
    }
    )
    public ResponseEntity<Moto> atualizarStatusAluguel(
            @PathVariable String placa,
            @RequestParam boolean alugada) {
        Moto motoAtualizada = motoService.atualizarStatusAluguel(placa, alugada);
        return ResponseEntity.ok(motoAtualizada);
    }

    @Operation(
            summary = "Alocar moto existente em posição livre",
            description = "Aloca uma moto já cadastrada no sistema em uma posição livre e sequencial no pátio especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto alocada com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Moto ou posição não encontrada", content = @Content)
            }
    )
    @PostMapping("/{placa}/alocar/{idPatio}")
    public ResponseEntity<ResponsePosicao> alocarMotoExistente(
            @PathVariable String placa,
            @PathVariable Long idPatio) {

        ResponsePosicao response = motoService.alocarMotoExistente(placa, idPatio);
        return ResponseEntity.ok(response);
    }
}

