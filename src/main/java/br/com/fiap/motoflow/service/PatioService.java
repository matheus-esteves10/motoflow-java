package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.responses.PatioQuantityResponse;
import br.com.fiap.motoflow.dto.responses.PatioResponse;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.SetorPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.SetorPatioRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PatioService {

    private final PatioRepository patioRepository;
    private final SetorPatioRepository setorPatioRepository;

    public PatioService(PatioRepository patioRepository, SetorPatioRepository setorPatioRepository) {
        this.patioRepository = patioRepository;
        this.setorPatioRepository = setorPatioRepository;
    }

    public PatioQuantityResponse getPatioInfos(final Long id){
        final Patio patio = patioRepository.findById(id).orElseThrow(() -> new PatioNotFoundException(id));

        final List<SetorPatio> setores = setorPatioRepository.findAllByPatioId(id);

        Set<String> setoresComPosicoesDisponiveis = new HashSet<>();
        Set<String> setoresCheios = new HashSet<>();

        for (var setor : setores) {
            if (setor.getMotos().size() < setor.getCapacidadeSetor()) {
                setoresComPosicoesDisponiveis.add(setor.getSetor());
            } else {
                setoresCheios.add(setor.getSetor());
            }
        }
        return PatioQuantityResponse.builder()
                .id(patio.getId())
                .apelido(patio.getApelido())
                .area(patio.getArea())
                .endereco(patio.getEndereco())
                .capacidadeMax(patio.getCapacidade())
                .setoresComPosicoesDisponiveis(setoresComPosicoesDisponiveis)
                .setoresCheios(setoresCheios)
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


}
