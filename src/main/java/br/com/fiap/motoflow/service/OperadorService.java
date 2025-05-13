package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.OperadorDto;
import br.com.fiap.motoflow.exceptions.OperadorNotFoundException;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.repository.OperadorRepository;
import br.com.fiap.motoflow.repository.PatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private PatioRepository patioRepository;

    public Operador salvarOperador(OperadorDto operadorDto) {

        Long patioId = operadorDto.patio().getId();
        Optional<Patio> patioExistente = patioRepository.findById(patioId);

        if (patioExistente.isEmpty()) {
            throw new PatioNotFoundException("Patio não encontrado.");
        }

        Operador operador = new Operador();
        operador.setNome(operadorDto.nome());
        operador.setSenha(operadorDto.senha());
        operador.setPatio(patioExistente.get());

        return operadorRepository.save(operador);
    }

    public Operador atualizarOperador(Long id, OperadorDto operadorDto) {

        Optional<Operador> operadorExistente = buscarOperadorPorId(id);

        if (operadorExistente.isEmpty()) {
            throw new OperadorNotFoundException("Operador não encontrado.");
        }

        Long patioId = operadorDto.patio().getId();
        Optional<Patio> patioExistente = patioRepository.findById(patioId);

        if (patioExistente.isEmpty()) {
            throw new PatioNotFoundException("Patio não encontrado.");
        }

        Operador operador = operadorExistente.get();
        operador.setNome(operadorDto.nome());
        operador.setSenha(operadorDto.senha());
        operador.setPatio(patioExistente.get());

        return operadorRepository.save(operador);
    }

    // Método de listagem com paginação
    public Page<Operador> listarOperadores(Pageable pageable) {
        return operadorRepository.findAll(pageable); // Usa o método findAll(Pageable pageable) do JpaRepository
    }

    public void excluirOperador(Long id) {
        Optional<Operador> operadorExistente = buscarOperadorPorId(id);

        if (operadorExistente.isEmpty()) {
            throw new OperadorNotFoundException("Operador não encontrado.");
        }

        operadorRepository.deleteById(id);
    }

    public Optional<Operador> buscarOperadorPorId(Long id) {
        return Optional.ofNullable(operadorRepository.findById(id)
                .orElseThrow(() -> new OperadorNotFoundException("Operador não encontrado.")));
    }
}
