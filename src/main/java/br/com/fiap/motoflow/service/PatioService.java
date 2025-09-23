package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.dto.responses.PatioResponse;
import br.com.fiap.motoflow.dto.web.PatioDashboardResponse;
import br.com.fiap.motoflow.dto.web.SetorDashboardDto;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.SetorPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.SetorPatioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatioService {

    private final PatioRepository patioRepository;
    private final SetorPatioRepository setorPatioRepository;

    public PatioService(PatioRepository patioRepository, SetorPatioRepository setorPatioRepository) {
        this.patioRepository = patioRepository;
        this.setorPatioRepository = setorPatioRepository;
    }



    public PatioQuantityResponse getPatioInfos(final Long id){
        final Patio patio = patioExiste(id);
        final List<SetorPatio> setores = setorPatioRepository.findAllByPatioId(id);


        final int capacidadeMax = patio.getCapacidade();
        final int quantidadeOcupadas = setores.stream()
                .mapToInt(setor -> setor.getMotos() != null ? setor.getMotos().size() : 0)
                .sum();
        final int quantidadeDisponiveis = capacidadeMax - quantidadeOcupadas;

        return PatioQuantityResponse.builder()
                .id(patio.getId())
                .apelido(patio.getApelido())
                .area(patio.getArea())
                .endereco(patio.getEndereco())
                .capacidadeMax(capacidadeMax)
                .quantidadeOcupadas(quantidadeOcupadas)
                .quantidadeDisponiveis(quantidadeDisponiveis)
                .build();
    }

    public Patio getPatioById(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new PatioNotFoundException(id));
    }

    public List<PatioResponse> getAllPatios() {
        final List<Patio> patios = patioRepository.findAll();
        return patios.stream()
                .map(patio -> new PatioResponse(patio.getId(), patio.getApelido(), patio.getCapacidade(), patio.getArea(),
                        patio.getEndereco().getLogradouro(), patio.getEndereco().getCidade(),
                        patio.getEndereco().getSiglaEstado(), patio.getEndereco().getCep(),
                        patio.getEndereco().getNumero()))
                .toList();
    }


    private Patio patioExiste(final Long patioId) {
        return patioRepository.findById(patioId).orElseThrow(() -> new PatioNotFoundException(patioId));
    }

/* THYMELEAF - DASHBOARD DO PÁTIO */

    public PatioDashboardResponse getDashboardInfos(final Long id) {
        final Patio patio = patioExiste(id);
        final List<SetorPatio> setores = setorPatioRepository.findAllByPatioId(id);

        List<SetorDashboardDto> setoresDashboard = setores.stream()
                .map(setor -> {
                    int posicoesOcupadas = setor.getMotos() != null ? setor.getMotos().size() : 0;
                    int vagasLivres = setor.getCapacidadeSetor() - posicoesOcupadas;

                    return SetorDashboardDto.builder()
                            .nomeSetor(setor.getSetor())
                            .capacidadeTotal(setor.getCapacidadeSetor())
                            .posicoesOcupadas(posicoesOcupadas)
                            .vagasLivres(vagasLivres)
                            .build();
                })
                .toList();

        int totalPosicoesOcupadas = setoresDashboard.stream()
                .mapToInt(SetorDashboardDto::getPosicoesOcupadas)
                .sum();

        int totalVagasLivres = setoresDashboard.stream()
                .mapToInt(SetorDashboardDto::getVagasLivres)
                .sum();

        return PatioDashboardResponse.builder()
                .id(patio.getId())
                .apelido(patio.getApelido())
                .area(patio.getArea())
                .endereco(patio.getEndereco())
                .capacidadeMax(patio.getCapacidade())
                .setores(setoresDashboard)
                .totalPosicoesOcupadas(totalPosicoesOcupadas)
                .totalVagasLivres(totalVagasLivres)
                .build();
    }

}
