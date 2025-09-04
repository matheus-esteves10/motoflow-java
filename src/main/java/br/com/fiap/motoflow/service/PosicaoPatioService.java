package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.CadastroPosicaoDto;
import br.com.fiap.motoflow.dto.responses.CadastroPosicaoResponseDto;
import br.com.fiap.motoflow.dto.responses.MotoHorizontalDto;
import br.com.fiap.motoflow.dto.responses.MotoResponseDto;
import br.com.fiap.motoflow.dto.responses.PosicaoPatioResponseDto;
import br.com.fiap.motoflow.dto.responses.PosicoesHorizontaisDto;
import br.com.fiap.motoflow.exceptions.ExceededSpaceException;
import br.com.fiap.motoflow.exceptions.PatioNotFoundException;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.PosicaoPatio;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.PosicaoPatioRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

        final int maiorExistente = maiorVerticalExistente(patio, dto.posicaoHorizontal());

        // quantidade de novas posições que serão criadas
        final int novasQtd = Math.max(0, dto.posicaoVerticalMax() - maiorExistente);

        // quantas posições já existem no pátio
        final int posicoesAtuais = posicaoPatioRepository.countByPatioId(patio.getId());

        // valida se a soma vai estourar a capacidade do pátio
        if (posicoesAtuais + novasQtd > patio.getCapacidade()) {
            throw new ExceededSpaceException(patio.getApelido(), patio.getCapacidade());
        }

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

    public List<PosicoesHorizontaisDto> posicoesHorizontais(final Long idPatio) {

        List<String> posicoesHorizontais = posicaoPatioRepository.posicoesHorizontais(idPatio)
                .orElseThrow(() -> new PatioNotFoundException("Pátio não encontrado com ID: " + idPatio));


        return posicoesHorizontais.stream()
                .map(PosicoesHorizontaisDto::new)
                .collect(Collectors.toList());
    }

    public MotoHorizontalDto motosPorPosicaoHorizontal(final Long patioId, final String posicaoHorizontal) {
        final List<PosicaoPatio> posicoes = posicaoPatioRepository.findAllByPatioIdAndPosicaoHorizontal(patioId, posicaoHorizontal);

        final int vagasTotais = posicaoPatioRepository.countByPatioIdAndHorizontal(patioId, posicaoHorizontal);

        List<MotoResponseDto> motos = posicoes.stream()
                .filter(posicao -> posicao.getMoto() != null)
                .sorted(Comparator.comparingInt(PosicaoPatio::getPosicaoVertical))
                .map(posicao -> new MotoResponseDto(
                        posicao.getMoto().getId(),
                        posicao.getMoto().getPlaca(),
                        posicao.getMoto().getTipoMoto(),
                        posicao.getMoto().getAno(),
                        posicao.getMoto().getStatusMoto(),
                        posicao.getPosicaoVertical()
                ))
                .toList();

        return new MotoHorizontalDto(posicaoHorizontal, vagasTotais, motos);
    }


    private int maiorVerticalExistente(final Patio patio, final String posicaoHorizontal) {
        return posicaoPatioRepository
                .findMaxVerticalByPatioAndHorizontal(patio, posicaoHorizontal);
    }

    private Patio checkPatio(final Long idPatio) {
        return patioRepository.findById(idPatio)
                .orElseThrow(() -> new PatioNotFoundException(
                        "Pátio com id " + idPatio + " não encontrado"));
    }

}
