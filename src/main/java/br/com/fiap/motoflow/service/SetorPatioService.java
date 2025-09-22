package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.CriarSetorDto;
import br.com.fiap.motoflow.dto.responses.SetoresDto;
import br.com.fiap.motoflow.dto.responses.SetorDto;
import br.com.fiap.motoflow.dto.responses.MotoResponseDto;
import br.com.fiap.motoflow.exceptions.*;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.SetorPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.SetorPatioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<SetoresDto> getSetores(final Long idPatio) {
        patioExiste(idPatio);

        final List<SetorPatio> setores = setorPatioRepository.findAllByPatioId(idPatio);

        return setores.stream()
                .map(setor -> {
                    final int capacidadeSetor = setor.getCapacidadeSetor();
                    final int motosOcupadas = setor.getMotos() != null ? setor.getMotos().size() : 0;
                    final int vagasDisponiveis = capacidadeSetor - motosOcupadas;

                    return new SetoresDto(
                            setor.getSetor(),
                            capacidadeSetor,
                            motosOcupadas,
                            vagasDisponiveis
                    );
                })
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

    @Transactional
    public SetorDto criarSetorNoPatio(final Long patioId, final CriarSetorDto dto) {
        final Patio patio = patioExiste(patioId);

        final String setorNome = dto.setor().toUpperCase();
        final boolean setorJaExiste = setorPatioRepository.findSetor(setorNome, patioId).isPresent();
        if (setorJaExiste) {
            throw new SetorAlreadyExists(setorNome, patio.getApelido());
        }

        final List<SetorPatio> setores = setorPatioRepository.findAllByPatioId(patioId);

        final int capacidadeTotalSetores = setores.stream().mapToInt(SetorPatio::getCapacidadeSetor).sum();
        final int capacidadeAposInsercao = capacidadeTotalSetores + dto.capacidadeSetor();
        if (capacidadeAposInsercao > patio.getCapacidade()) {
            throw new ExceededSpaceException(patio.getApelido(), capacidadeTotalSetores, capacidadeAposInsercao, patio.getCapacidade());
        }

        final SetorPatio novoSetor = SetorPatio.builder()
                .setor(setorNome)
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

    @Transactional
    public void deletarSetor(final String setor, final Long patioId) {
        final SetorPatio setorDeletar = setorExisite(setor, patioId);

        if (setorDeletar.getMotos() != null && !setorDeletar.getMotos().isEmpty()) {
            throw new SetorComMotosException();
        }

        setorPatioRepository.delete(setorDeletar);
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
