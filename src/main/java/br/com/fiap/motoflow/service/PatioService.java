package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.dto.responses.PatioResponse;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.repository.PatioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatioService {

    private final PatioRepository patioRepository;

    public PatioService(PatioRepository patioRepository) {
        this.patioRepository = patioRepository;
    }

    public PatioQuantityResponse getPatioInfos (final Long id){

        final Patio patio = patioRepository.findById(id).orElseThrow(() -> new PatioNotFoundException("Patio nao encontrado"));

        final int posicoesDisponiveis = patioRepository.countPosicoesDisponiveis(id);

        final int posicoesOcupadas = patioRepository.countPosicoesOcupadas(id);

        return PatioQuantityResponse.builder()
                .capacidadeMax(patio.getCapacidade())
                .posicoesDisponiveis(posicoesDisponiveis)
                .posicoesOcupadas(posicoesOcupadas)
                .build();
    }

    public Patio getPatioById(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new PatioNotFoundException("Pátio não encontrado."));
    }

    public List<PatioResponse> getAllPatios() {
        List<Patio> patios = patioRepository.findAll();
        return patios.stream()
                .map(patio -> new PatioResponse(patio.getId(), patio.getApelido(), patio.getCapacidade(), patio.getArea(),
                        patio.getEndereco().getLogradouro(), patio.getEndereco().getCidade(),
                        patio.getEndereco().getSiglaEstado(), patio.getEndereco().getCep(),
                        patio.getEndereco().getNumero()))
                .toList();
    }


}
