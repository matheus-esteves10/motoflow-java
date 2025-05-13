package br.com.fiap.motoflow.controller;

import br.com.fiap.motoflow.dto.OperadorDto;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.service.OperadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(
            summary = "Cadastrar operador",
            description = "Cria um novo operador com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operador criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operador.class))),
                    @ApiResponse(responseCode = "404", description = "Pátio não encontrado", content = @Content)
            }
    )
    public ResponseEntity<Operador> criarOperador(@RequestBody OperadorDto operadorDto) {
        Operador operadorCriado = operadorService.salvarOperador(operadorDto);
        return new ResponseEntity<>(operadorCriado, HttpStatus.CREATED);
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
    public ResponseEntity<Operador> atualizarOperador(@PathVariable Long id, @RequestBody OperadorDto operadorDto) {
        Operador operadorAtualizado = operadorService.atualizarOperador(id, operadorDto);
        return new ResponseEntity<>(operadorAtualizado, HttpStatus.OK);
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
    public ResponseEntity<Operador> buscarOperadorPorId(@PathVariable Long id) {
        Operador operador = operadorService.buscarOperadorPorId(id).orElseThrow();
        return new ResponseEntity<>(operador, HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Listar operadores",
            description = "Lista todos os operadores com paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de operadores", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
            }
    )
    public ResponseEntity<Page<Operador>> listarOperadores(Pageable pageable) {
        Page<Operador> operadores = operadorService.listarOperadores(pageable);
        return new ResponseEntity<>(operadores, HttpStatus.OK);
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
