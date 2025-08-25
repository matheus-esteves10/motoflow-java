package br.com.fiap.motoflow.controller.api;

import br.com.fiap.motoflow.dto.OperadorDto;
import br.com.fiap.motoflow.dto.responses.OperadorResponse;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.service.OperadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/operador")
@SecurityRequirement(name = "bearerAuth")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    @PostMapping
    @CacheEvict(value = "operadores", allEntries = true)
    @PreAuthorize("hasRole('ADMIN')")
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

    @PutMapping("/me")
    @CacheEvict(value = "operadores", allEntries = true)
    @Operation(
            summary = "Atualizar operador",
            description = "Atualiza os dados de um operador existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operador atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operador.class))),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado", content = @Content)
            }
    )
    public ResponseEntity<OperadorResponse> atualizarOperador(@RequestBody OperadorDto operadorDto, @AuthenticationPrincipal Operador operador) {

        Operador operadorAtualizado = operadorService.atualizarOperador(operadorDto, operador);
        return new ResponseEntity<>(new OperadorResponse(operadorAtualizado), HttpStatus.OK);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Buscar operador por ID",
            description = "Recupera os detalhes de um operador através do ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operador encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Operador.class))),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado", content = @Content)
            }
    )
    public ResponseEntity<OperadorResponse> operadorAuth(@AuthenticationPrincipal Operador operador) {

        var response =operadorService.operadorAuth(operador).orElseThrow();
        return new ResponseEntity<>(new OperadorResponse(response), HttpStatus.OK);
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
    @CacheEvict(value = "operadores", allEntries = true)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir operador",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Operador excluído com sucesso", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado", content = @Content)
            }
    )
    public ResponseEntity<Void> excluirOperador(@PathVariable Long id) {

        operadorService.excluirOperador(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OperadorResponse> tornarOperadorAdmin(@PathVariable Long id) {
        Operador operadorAtualizado = operadorService.tornarAdmin(id);
        return new ResponseEntity<>(new OperadorResponse(operadorAtualizado), HttpStatus.OK);
    }
}
