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
import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PosicaoPatioRepository posicaoPatioRepository;

    public Moto save(MotoDto motoDto) {
        Moto moto = new Moto(motoDto.tipoMoto(), motoDto.ano(), motoDto.placa(), motoDto.precoAluguel(), motoDto.isAlugada(), motoDto.dataAlocacao());

        return motoRepository.save(moto);
    }

    public Page<Moto> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    public PosicaoMotoResponse buscarPosicaoPorPlaca(String placa) {
        PosicaoPatio posicao = posicaoPatioRepository.findByMotoPlaca(placa)
                .orElseThrow(() -> new MotoNotFoundException("Moto com placa '" + placa + "' não encontrada no pátio"));

        return new PosicaoMotoResponse(
                posicao.getMoto().getPlaca(),
                posicao.getMoto().getTipoMoto(),
                posicao.getMoto().getAno(),
                posicao.getMoto().getPrecoAluguel(),
                posicao.getPatio().getId(),
                posicao.getPatio().getApelido(),
                posicao.getPatio().getEndereco(),
                posicao.getPosicaoVertical(),
                posicao.getPosicaoHorizontal(),
                posicao.isPosicaoLivre()
        );
    }

    public void excluirMotoPorPlaca(String placa) {
        Moto moto = motoRepository.findByPlaca(placa)
                .orElseThrow(() -> new MotoNotFoundException("Moto com placa '" + placa + "' não encontrada"));

        if (!moto.isAlugada()) {
            posicaoPatioRepository.findByMotoPlaca(placa).ifPresent(posicao -> {
                posicao.setMoto(null);
                posicao.setPosicaoLivre(true);
                posicaoPatioRepository.save(posicao);
            });
        }

        motoRepository.delete(moto);
    }

    public ResponsePosicao alocarMotoNaPosicao(String placa, String posicaoHorizontal, int posicaoVertical) {
        Moto moto = motoRepository.findByPlaca(placa)
                .orElseThrow(() -> new MotoNotFoundException("Moto com placa '" + placa + "' não encontrada"));

        PosicaoPatio posicao = posicaoPatioRepository
                .findByPosicaoHorizontalAndPosicaoVerticalAndIsPosicaoLivreTrue(posicaoHorizontal, posicaoVertical)
                .orElseThrow(() -> new MotoNotFoundException("Posição " + posicaoHorizontal + posicaoVertical + " não encontrada ou já ocupada"));

        posicao.setMoto(moto);
        posicao.setPosicaoLivre(false);
        posicaoPatioRepository.save(posicao);

        return new ResponsePosicao(
                moto.getPlaca(),
                posicao.getPosicaoHorizontal(),
                posicao.getPosicaoVertical(),
                posicao.getPatio().getId()
        );
    }

    public ResponsePosicao cadastrarMotoEAlocar(MotoDto motoDto, Long idPatio) {

        Moto moto = save(motoDto); // Salva a moto


        PosicaoPatio posicaoLivre = posicaoPatioRepository // Busca a primeira posição livre para o pátio específico
                .findFirstByIsPosicaoLivreTrueAndPatioIdOrderByPosicaoHorizontalAscPosicaoVerticalAsc(idPatio)
                .orElseThrow(() -> new PosicaoNotFoundException("Nenhuma posição livre disponível no pátio com ID " + idPatio));

        // Aloca a moto
        posicaoLivre.setMoto(moto);
        posicaoLivre.setPosicaoLivre(false);
        posicaoPatioRepository.save(posicaoLivre);

        return new ResponsePosicao(
                moto.getPlaca(),
                posicaoLivre.getPosicaoHorizontal(),
                posicaoLivre.getPosicaoVertical(),
                posicaoLivre.getPatio().getId()
        );
    }

    @Transactional
    public Moto atualizarStatusAluguel(String placa, boolean isAlugada) {
        Moto moto = motoRepository.findByPlaca(placa)
                .orElseThrow(() -> new MotoNotFoundException("Moto com placa '" + placa + "' não encontrada"));

        moto.setAlugada(isAlugada);

        if (isAlugada) {
            moto.setDataAlocacao(LocalDate.now());

            posicaoPatioRepository.findByMotoPlaca(placa).ifPresent(posicao -> {
                posicao.setMoto(null);
                posicao.setPosicaoLivre(true);
                posicaoPatioRepository.save(posicao);
            });
        }

        return motoRepository.save(moto);
    }



}
