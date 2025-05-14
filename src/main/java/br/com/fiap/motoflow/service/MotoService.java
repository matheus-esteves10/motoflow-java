package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.MotoDto;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.exceptions.MotoNotFoundException;
import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.PosicaoPatio;
import br.com.fiap.motoflow.repository.MotoRepository;
import br.com.fiap.motoflow.repository.PosicaoPatioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PosicaoPatioRepository posicaoPatioRepository;

    public Moto save(MotoDto motoDto) {
        Moto moto = new Moto(motoDto.tipoMoto(), motoDto.ano(), motoDto.placa(), motoDto.precoAluguel(), motoDto.isAlugada());

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
}
