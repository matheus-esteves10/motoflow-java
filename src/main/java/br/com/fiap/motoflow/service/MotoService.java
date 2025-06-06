package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.MotoDto;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.dto.responses.ResponsePosicao;
import br.com.fiap.motoflow.exceptions.MotoNotFoundException;
import br.com.fiap.motoflow.exceptions.PosicaoNotFoundException;
import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.PosicaoPatio;
import br.com.fiap.motoflow.repository.MotoRepository;
import br.com.fiap.motoflow.repository.PosicaoPatioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PosicaoPatioRepository posicaoPatioRepository;


    @Transactional
    public Moto save(MotoDto motoDto) {
        return motoRepository.save(Moto.from(motoDto));
    }

    public Page<Moto> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    @Transactional
    public void excluirMotoPorPlaca(String placa) {
        Moto moto = buscarMotoOrException(placa);

        if (!moto.isAlugada()) {
            posicaoPatioRepository.findByMotoPlaca(placa).ifPresent(this::liberarPosicao);
        }

        motoRepository.delete(moto);
    }

    // --- ALOCAÇÃO ---

    @Transactional
    public ResponsePosicao cadastrarMotoEAlocar(MotoDto motoDto, Long idPatio) {
        Moto moto = save(motoDto);
        return alocarMotoEmPosicaoLivre(moto, idPatio);
    }

    @Transactional
    public ResponsePosicao alocarMotoExistente(String placa, Long idPatio) {
        Moto moto = buscarMotoOrException(placa);

        posicaoPatioRepository.findByMotoPlaca(placa).ifPresent(this::liberarPosicao);

        return alocarMotoEmPosicaoLivre(moto, idPatio);
    }

    @Transactional
    public ResponsePosicao alocarMotoNaPosicao(String placa, String posicaoHorizontal, int posicaoVertical) {
        Moto moto = buscarMotoOrException(placa);

        PosicaoPatio posicao = buscarPosicaoLivreOrException(posicaoHorizontal, posicaoVertical);

        alocarMoto(posicao, moto);

        return new ResponsePosicao(
                moto.getPlaca(),
                posicao.getPosicaoHorizontal(),
                posicao.getPosicaoVertical(),
                posicao.getPatio().getId()
        );
    }

    // --- ATUALIZAÇÃO DE STATUS ---

    @Transactional
    public Moto atualizarStatusAluguel(String placa, boolean isAlugada) {
        Moto moto = buscarMotoOrException(placa);
        moto.setAlugada(isAlugada);

        if (isAlugada) {
            moto.setDataAluguel(LocalDate.now());
            posicaoPatioRepository.findByMotoPlaca(placa).ifPresent(this::liberarPosicao);
        }

        return motoRepository.save(moto);
    }

    // --- CONSULTAS ---

    public PosicaoMotoResponse buscarPosicaoPorPlaca(String placa) {
        PosicaoPatio posicao = posicaoPatioRepository.findByMotoPlaca(placa)
                .orElseThrow(() -> new MotoNotFoundException("Moto com placa '" + placa + "' não encontrada no pátio"));

        Moto moto = posicao.getMoto();

        return new PosicaoMotoResponse(
                moto.getPlaca(),
                moto.getTipoMoto(),
                moto.getAno(),
                moto.getPrecoAluguel(),
                posicao.getPatio().getId(),
                posicao.getPatio().getApelido(),
                posicao.getPatio().getEndereco(),
                posicao.getPosicaoVertical(),
                posicao.getPosicaoHorizontal(),
                posicao.isPosicaoLivre()
        );
    }

    // --- MÉTODOS AUXILIARES PRIVADOS ---

    private Moto buscarMotoOrException(String placa) {
        return motoRepository.findByPlaca(placa)
                .orElseThrow(() -> new MotoNotFoundException("Moto com placa '" + placa + "' não encontrada"));
    }

    private PosicaoPatio buscarPosicaoLivreOrException(String horizontal, int vertical) {
        return posicaoPatioRepository
                .findByPosicaoHorizontalAndPosicaoVerticalAndIsPosicaoLivreTrue(horizontal, vertical)
                .orElseThrow(() -> new MotoNotFoundException("Posição " + horizontal + vertical + " não encontrada ou já ocupada"));
    }

    private ResponsePosicao alocarMotoEmPosicaoLivre(Moto moto, Long idPatio) {
        PosicaoPatio posicaoLivre = posicaoPatioRepository
                .findFirstByIsPosicaoLivreTrueAndPatioIdOrderByPosicaoHorizontalAscPosicaoVerticalAsc(idPatio)
                .orElseThrow(() -> new PosicaoNotFoundException("Nenhuma posição livre disponível no pátio com ID " + idPatio));

        alocarMoto(posicaoLivre, moto);

        return new ResponsePosicao(
                moto.getPlaca(),
                posicaoLivre.getPosicaoHorizontal(),
                posicaoLivre.getPosicaoVertical(),
                posicaoLivre.getPatio().getId()
        );
    }

    private void liberarPosicao(PosicaoPatio posicao) {
        posicao.setMoto(null);
        posicao.setPosicaoLivre(true);
        posicaoPatioRepository.save(posicao);
    }

    private void alocarMoto(PosicaoPatio posicao, Moto moto) {
        posicao.setMoto(moto);
        posicao.setPosicaoLivre(false);
        moto.setDataAluguel(null);;
        posicaoPatioRepository.save(posicao);
    }

}
