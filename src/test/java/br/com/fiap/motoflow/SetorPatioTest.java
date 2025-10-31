package br.com.fiap.motoflow;

import br.com.fiap.motoflow.dto.CriarSetorDto;
import br.com.fiap.motoflow.exceptions.ExceededSpaceException;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.exceptions.SetorAlreadyExists;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.SetorPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.SetorPatioRepository;
import br.com.fiap.motoflow.service.SetorPatioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SetorPatioService")
class SetorPatioTest {

    @Mock
    private SetorPatioRepository setorPatioRepository;

    @Mock
    private PatioRepository patioRepository;

    @InjectMocks
    private SetorPatioService setorPatioService;

    private Long patioId;
    private Patio patio;

    @BeforeEach
    void setUp() {
        patioId = 1L;
        patio = new Patio(patioId, "Pátio A", 100, null, null, null);
    }

    @Test
    @DisplayName("Deve criar setor com sucesso quando dados são válidos")
    void criarSetor_sucesso() {
        var dto = new CriarSetorDto("A", 10);
        when(patioRepository.findById(patioId)).thenReturn(Optional.of(patio));
        when(setorPatioRepository.findSetor("A", patioId)).thenReturn(Optional.empty());
        when(setorPatioRepository.save(any(SetorPatio.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = setorPatioService.criarSetorNoPatio(patioId, dto);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("A", result.setor()),
                () -> assertEquals(10, result.vagasTotais()),
                () -> assertEquals(0, result.ocupadas()),
                () -> assertEquals(10, result.disponiveis()),
                () -> assertTrue(result.motos().isEmpty())
        );

        verify(setorPatioRepository).save(any(SetorPatio.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando setor já existe no pátio")
    void criarSetor_quandoSetorJaExiste_deveLancarExcecao() {
        var dto = new CriarSetorDto("A", 5);
        when(patioRepository.findById(patioId)).thenReturn(Optional.of(patio));
        when(setorPatioRepository.findSetor("A", patioId))
                .thenReturn(Optional.of(new SetorPatio()));

        assertThrows(SetorAlreadyExists.class,
                () -> setorPatioService.criarSetorNoPatio(patioId, dto));

        verify(setorPatioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando setor excede capacidade total do pátio")
    void criarSetor_quandoExcedeCapacidade_deveLancarExcecao() {
        var dto = new CriarSetorDto("B", 10);
        var patioPequeno = new Patio(patioId, "Pátio A", 15, null, null, null);
        var existente = SetorPatio.builder().capacidadeSetor(10).setor("X").build();

        when(patioRepository.findById(patioId)).thenReturn(Optional.of(patioPequeno));
        when(setorPatioRepository.findSetor("B", patioId)).thenReturn(Optional.empty());
        when(setorPatioRepository.findAllByPatioId(patioId)).thenReturn(List.of(existente));

        assertThrows(ExceededSpaceException.class,
                () -> setorPatioService.criarSetorNoPatio(patioId, dto));

        verify(setorPatioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando pátio não existe")
    void criarSetor_quandoPatioNaoExiste_deveLancarExcecao() {
        var idInexistente = 99L;
        var dto = new CriarSetorDto("C", 5);
        when(patioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(PatioNotFoundException.class,
                () -> setorPatioService.criarSetorNoPatio(idInexistente, dto));

        verify(setorPatioRepository, never()).save(any());
    }
}


