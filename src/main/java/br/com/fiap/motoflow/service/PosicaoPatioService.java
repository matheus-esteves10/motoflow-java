package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.CadastroPosicaoDto;
import br.com.fiap.motoflow.dto.responses.CadastroPosicaoResponseDto;
import br.com.fiap.motoflow.dto.responses.MotoResponseDto;
import br.com.fiap.motoflow.dto.responses.PosicaoPatioResponseDto;
import br.com.fiap.motoflow.dto.responses.PosicoesHorizontaisDto;
import br.com.fiap.motoflow.exceptions.EmptyPositionException;
import br.com.fiap.motoflow.exceptions.ExceededSpaceException;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.PosicaoPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.PosicaoPatioRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PosicaoPatioService {

    private final PosicaoPatioRepository posicaoPatioRepository;
    private final PatioRepository patioRepository;

    public PosicaoPatioService(PosicaoPatioRepository posicaoPatioRepository, PatioRepository patioRepository) {
        this.patioRepository = patioRepository;
        this.posicaoPatioRepository = posicaoPatioRepository;
    }

    public CadastroPosicaoResponseDto cadastrarPosicao(final CadastroPosicaoDto dto) {

        final Patio patio = checkPatio(dto.idPatio());

        if (maxPosicoesAtingido(patio.getId(), patio.getCapacidade()) || dto.posicaoVerticalMax() > patio.getCapacidade()) {
            throw new ExceededSpaceException(patio.getApelido(), patio.getCapacidade());
        }

        final int maiorExistente = maiorVerticalExistente(patio, dto.posicaoHorizontal());

        Set<PosicaoPatio> novasPosicoes = new LinkedHashSet<>();
        String mensagem = null;

        for (int i = maiorExistente + 1; i <= dto.posicaoVerticalMax(); i++) {
            PosicaoPatio posicaoPatio = new PosicaoPatio();
            posicaoPatio.setPosicaoVertical(i);
            posicaoPatio.setPosicaoHorizontal(dto.posicaoHorizontal().toUpperCase());
            posicaoPatio.setPosicaoLivre(true);
            posicaoPatio.setPatio(patio);

            novasPosicoes.add(posicaoPatioRepository.save(posicaoPatio));
        }

        if (maiorExistente >= dto.posicaoVerticalMax()) {
            mensagem = "Nenhuma nova posição cadastrada, todas já estavam ocupadas.";
        } else if (maiorExistente > 0) {
            mensagem = "Posições até " + dto.posicaoHorizontal() + maiorExistente +
                    " já existiam. Foram cadastradas apenas as posteriores.";
        }

        Set<PosicaoPatioResponseDto> novasPosicoesDto = novasPosicoes.stream()
                .map(PosicaoPatioResponseDto::fromEntity)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new CadastroPosicaoResponseDto(novasPosicoesDto, mensagem);
    }

    public List<PosicoesHorizontaisDto> posicoesHorizontais(Long idPatio) {

        List<String> posicoesHorizontais = posicaoPatioRepository.posicoesHorizontais(idPatio)
                .orElseThrow(() -> new PatioNotFoundException("Pátio não encontrado com ID: " + idPatio));


        return posicoesHorizontais.stream()
                .map(PosicoesHorizontaisDto::new)
                .collect(Collectors.toList());
    }

    public List<MotoResponseDto> motosPorPosicaoHorizontal(Long patioId, String posicaoHorizontal) {
        List<PosicaoPatio> posicoes = posicaoPatioRepository.findAllByPatioIdAndPosicaoHorizontal(patioId, posicaoHorizontal);
        return posicoes.stream()
                .map(posicao -> MotoResponseDto.from(posicao.getMoto()))
                .collect(Collectors.toList());
    }


    private int maiorVerticalExistente(Patio patio, String posicaoHorizontal) {
        return posicaoPatioRepository
                .findMaxVerticalByPatioAndHorizontal(patio, posicaoHorizontal);
    }

    private Patio checkPatio(Long idPatio) {
        return patioRepository.findById(idPatio)
                .orElseThrow(() -> new PatioNotFoundException(
                        "Pátio com id " + idPatio + " não encontrado"));
    }

    private boolean maxPosicoesAtingido(Long patioId, int maxPosicoes) {
        int posicoesAtuais = posicaoPatioRepository.countByPatioId(patioId);
        return posicoesAtuais > maxPosicoes;
    }

}
