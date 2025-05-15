package br.com.fiap.motoflow.controller;

import br.com.fiap.motoflow.dto.OperadorDto;
import br.com.fiap.motoflow.dto.responses.OperadorResponse;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.service.OperadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operadores")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    @PostMapping
    @CacheEvict(value = "operadores", allEntries = true)
    @Operation(
            summary = "Cadastrar operador",
            description = "Cria um novo operador com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operador criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operador.class))),
                    @ApiResponse(responseCode = "404", description = "Pátio não encontrado", content = @Content)
            }
    )
    public ResponseEntity<OperadorResponse> criarOperador(@RequestBody OperadorDto operadorDto) {
        Operador operadorCriado = operadorService.salvarOperador(operadorDto);
        return new ResponseEntity<>(new OperadorResponse(operadorCriado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar operador",
            description = "Atualiza os dados de um operador existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operador atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operador.class))),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado", content = @Content)
            }
    )
    public ResponseEntity<OperadorResponse> atualizarOperador(@PathVariable Long id, @RequestBody OperadorDto operadorDto) {
        Operador operadorAtualizado = operadorService.atualizarOperador(id, operadorDto);
        return new ResponseEntity<>(new OperadorResponse(operadorAtualizado), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar operador por ID",
            description = "Recupera os detalhes de um operador através do ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operador encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operador.class))),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado", content = @Content)
            }
    )
    public ResponseEntity<OperadorResponse> buscarOperadorPorId(@PathVariable Long id) {
        Operador operador = operadorService.buscarOperadorPorId(id).orElseThrow();
        return new ResponseEntity<>(new OperadorResponse(operador), HttpStatus.OK);
    }

    @GetMapping
    @Cacheable(value = "operadores")
    @Operation(
            summary = "Listar operadores",
            description = "Lista todos os operadores com paginação. Use o parâmetro ?sort=nome,asc para ordenar por nome.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de operadores", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
            }
    )
    public ResponseEntity<Page<OperadorResponse>> listarOperadores(Pageable pageable) {
        Page<Operador> operadores = operadorService.listarOperadores(pageable);
        Page<OperadorResponse> operadorResponses = operadores.map(OperadorResponse::new);
        return new ResponseEntity<>(operadorResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir operador",
            description = "Exclui um operador existente pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Operador excluído com sucesso", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado", content = @Content)
            }
    )
    public ResponseEntity<Void> excluirOperador(@PathVariable Long id) {
        operadorService.excluirOperador(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
