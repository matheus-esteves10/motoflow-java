package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.OperadorDto;
import br.com.fiap.motoflow.exceptions.OperadorNotFoundException;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Operador;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.repository.OperadorRepository;
import br.com.fiap.motoflow.repository.PatioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public Operador salvarOperador(OperadorDto operadorDto) {

        Long patioId = operadorDto.patio().getId();
        Optional<Patio> patioExistente = patioRepository.findById(patioId);

        if (patioExistente.isEmpty()) {
            throw new PatioNotFoundException("Patio não encontrado.");
        }

        Operador operador = new Operador();
        operador.setNome(operadorDto.nome());
        operador.setSenha(passwordEncoder.encode(operadorDto.senha()));
        operador.setPatio(patioExistente.get());

        return operadorRepository.save(operador);
    }

    @Transactional
    public Operador atualizarOperador(OperadorDto operadorDto, Operador operador) {

        Long patioId = operadorDto.patio().getId();
        Optional<Patio> patioExistente = patioRepository.findById(patioId);

        if (patioExistente.isEmpty()) {
            throw new PatioNotFoundException("Patio não encontrado.");
        }

        operador.setNome(operadorDto.nome());
        operador.setSenha(passwordEncoder.encode(operadorDto.senha()));
        operador.setPatio(patioExistente.get());

        return operadorRepository.save(operador);
    }

    public Page<Operador> listarOperadores(Pageable pageable) {
        return operadorRepository.findAll(pageable);
    }

    @Transactional
    public void excluirOperador(Operador operador) {

        operadorRepository.deleteById(operador.getId());
    }

    public Optional<Operador> operadorAuth(Operador operador) {

        return Optional.ofNullable(operadorRepository.findById(operador.getId())
                .orElseThrow(() -> new OperadorNotFoundException("Operador não encontrado.")));
    }
}
