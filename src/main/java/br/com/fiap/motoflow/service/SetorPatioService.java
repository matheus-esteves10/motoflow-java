package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.CriarSetorDto;
import br.com.fiap.motoflow.dto.responses.PosicoesHorizontaisDto;
import br.com.fiap.motoflow.dto.responses.SetorDto;
import br.com.fiap.motoflow.dto.responses.MotoResponseDto;
import br.com.fiap.motoflow.exceptions.ExceededSpaceException;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.exceptions.SetorNaoExisteException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.SetorPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.SetorPatioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorPatioService {

    private final SetorPatioRepository setorPatioRepository;
    private final PatioRepository patioRepository;

    public SetorPatioService(SetorPatioRepository setorPatioRepository, PatioRepository patioRepository) {
        this.setorPatioRepository = setorPatioRepository;
        this.patioRepository = patioRepository;

    }

    public List<PosicoesHorizontaisDto> getSetores(final Long idPatio) {

        final List<String> posicoesHorizontais = setorPatioRepository.posicoesHorizontais(idPatio)
                .orElseThrow(() -> new PatioNotFoundException(idPatio));


        return posicoesHorizontais.stream()
                .map(PosicoesHorizontaisDto::new)
                .collect(Collectors.toList());
    }


    public SetorDto motosPorSetor(final Long patioId, final String setor) {
        var setorPatio = setorExisite(setor, patioId);

        final int vagasTotais = setorPatio.getCapacidadeSetor();
        final int ocupadas = setorPatio.getMotos() != null ? setorPatio.getMotos().size() : 0;
        final int disponiveis = vagasTotais - ocupadas;

        final List<MotoResponseDto> motos = setorPatio.getMotos() == null ? List.of() : setorPatio.getMotos().stream()
                .map(m -> new MotoResponseDto(
                        m.getId(),
                        m.getPlaca(),
                        m.getTipoMoto(),
                        m.getAno(),
                        m.getStatusMoto()
                ))
                .collect(Collectors.toList());

        return new SetorDto(
                setorPatio.getSetor(),
                vagasTotais,
                ocupadas,
                disponiveis,
                motos
        );
    }

    public SetorDto criarSetorNoPatio(final Long patioId, final CriarSetorDto dto) {
        final Patio patio = patioExiste(patioId);

        final List<SetorPatio> setores = setorPatioRepository.findAllByPatioId(patioId);

        final int capacidadeTotalSetores = setores.stream().mapToInt(SetorPatio::getCapacidadeSetor).sum();
        final int capacidadeAposInsercao = capacidadeTotalSetores + dto.capacidadeSetor();
        if (capacidadeAposInsercao > patio.getCapacidade()) {
            throw new ExceededSpaceException(patio.getApelido(), capacidadeTotalSetores, capacidadeAposInsercao, patio.getCapacidade());
        }

        final SetorPatio novoSetor = SetorPatio.builder()
                .setor(dto.setor())
                .capacidadeSetor(dto.capacidadeSetor())
                .patio(patio)
                .build();

        setorPatioRepository.save(novoSetor);

        return new SetorDto(
                novoSetor.getSetor(),
                novoSetor.getCapacidadeSetor(),
                0,
                novoSetor.getCapacidadeSetor(),
                List.of()
        );
    }


    private SetorPatio setorExisite(String setor, Long patioId) {
        patioExiste(patioId);
        return setorPatioRepository.findSetor(setor, patioId)
                .orElseThrow(() -> new SetorNaoExisteException(setor));
    }

    private Patio patioExiste(final Long patioId) {
        return patioRepository.findById(patioId).orElseThrow(() -> new PatioNotFoundException(patioId));
    }


}
