package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.repository.PatioRepository;
import org.springframework.stereotype.Service;

@Service
public class PatioService {

    private final PatioRepository patioRepository;

    public PatioService(PatioRepository patioRepository) {
        this.patioRepository = patioRepository;
    }

    public PatioQuantityResponse getPatioInfos (final Long id){

        final Patio patio = patioRepository.findById(id).orElseThrow(() -> new PatioNotFoundException("Patio nao encontrado"));

        final int posicoesDisponiveis = patioRepository.countPosicoesDisponiveis(id);

        return PatioQuantityResponse.builder()
                .capacidadeMax(patio.getCapacidade())
                .posicoesDisponiveis(posicoesDisponiveis)
                .quantidadeAlugadas(patio.getCapacidade() - posicoesDisponiveis)
                .build();
    }

    public Patio getPatioById(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new PatioNotFoundException("Pátio não encontrado."));
    }


}
