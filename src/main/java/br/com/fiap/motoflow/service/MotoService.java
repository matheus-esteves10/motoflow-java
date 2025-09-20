package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.dto.CadastroMotoDto;
import br.com.fiap.motoflow.dto.EditarStatusMotoDto;
import br.com.fiap.motoflow.dto.SetorMotoDto;
import br.com.fiap.motoflow.dto.responses.PosicaoMotoResponse;
import br.com.fiap.motoflow.dto.responses.ResponseMovimentacao;
import br.com.fiap.motoflow.exceptions.*;
import br.com.fiap.motoflow.model.Moto;
import br.com.fiap.motoflow.model.Patio;
import br.com.fiap.motoflow.model.SetorPatio;
import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import br.com.fiap.motoflow.repository.MotoRepository;
import br.com.fiap.motoflow.repository.PatioRepository;
import br.com.fiap.motoflow.repository.SetorPatioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final SetorPatioRepository setorPatioRepository;
    private final PatioRepository patioRepository;

    public MotoService(MotoRepository motoRepository, SetorPatioRepository setorPatioRepository, PatioRepository patioRepository) {
        this.motoRepository = motoRepository;
        this.setorPatioRepository = setorPatioRepository;
        this.patioRepository = patioRepository;
    }

    @Transactional
    public ResponseMovimentacao alocarMoto(final CadastroMotoDto motoDto, final Long patioId) {
        alocarNoSetor(motoDto, patioId);

        return ResponseMovimentacao.builder().
                placa(motoDto.placa()).
                tipoMoto(motoDto.tipoMoto()).
                ano(motoDto.ano()).
                precoAluguel(motoDto.precoAluguel()).
                statusMoto(StatusMoto.DISPONIVEL).
                codRastreador(motoDto.codRastreador()).
                dataEntrada(motoDto.dataEntrada()).
                setor(motoDto.setor()).
                build();
    }

    @Transactional
    public ResponseMovimentacao alterarSetor(final SetorMotoDto dto, final Long patioId) {
        Moto moto = buscarMotoOrException(dto.placa(), dto.codRastreador());

        if (moto.getStatusMoto().equals(StatusMoto.ALUGADA) || moto.getStatusMoto().equals(StatusMoto.MANUTENCAO)) {
            throw new MotoIndisponivelException(dto.placa());
        }

        final SetorPatio setorPatio = setorExisite(dto.setor(), patioId);

        moto.setSetorPatio(setorPatio);
        moto.setPatioId(patioId);
        motoRepository.save(moto);

        return ResponseMovimentacao.builder().
                placa(moto.getPlaca()).
                tipoMoto(moto.getTipoMoto()).
                ano(moto.getAno()).
                precoAluguel(moto.getPrecoAluguel()).
                statusMoto(moto.getStatusMoto()).
                codRastreador(moto.getCodRastreador()).
                dataEntrada(moto.getDataEntrada()).
                setor(moto.getSetorPatio().getSetor()).
                build();
    }



    @Transactional
    public ResponseMovimentacao alterarStatusMoto(final EditarStatusMotoDto edicaoDto, final String placa) {
        Moto moto = buscarMotoOrException(placa, null);

        if (edicaoDto.status().equals(StatusMoto.ALUGADA)) {
            moto.setDataAluguel(LocalDate.now());
            moto.setDataEntrada(null);
            removerDoSetor(moto);
            moto.setPatioId(null);
            moto.setCodRastreador(null);
        } else if (edicaoDto.status().equals(StatusMoto.MANUTENCAO)) {
            removerDoSetor(moto);
        }

        moto.setStatusMoto(edicaoDto.status());

        motoRepository.save(moto);

        return ResponseMovimentacao.builder().
                placa(moto.getPlaca()).
                tipoMoto(moto.getTipoMoto()).
                ano(moto.getAno()).
                precoAluguel(moto.getPrecoAluguel()).
                statusMoto(moto.getStatusMoto()).
                codRastreador(moto.getCodRastreador()).
                dataEntrada(moto.getDataEntrada()).
                setor(moto.getSetorPatio() != null ? moto.getSetorPatio().getSetor() : null).
                build();

    }

    @Transactional
    public void deletarMoto(final String placa) {
        final Moto moto = buscarMotoOrException(placa, null);
        removerDoSetor(moto);
        motoRepository.delete(moto);
    }


    // --- CONSULTAS ---

    public PosicaoMotoResponse buscarSetorPorPlaca(final String placa) {
        final Moto moto = buscarMotoOrException(placa, null);
        return construirPosicaoMotoResponse(moto);
    }

    public PosicaoMotoResponse buscarSetorPorRastreador(final String codRastreador) {
        final Moto moto = buscarMotoOrException(null, codRastreador);
        return construirPosicaoMotoResponse(moto);
    }

    public PosicaoMotoResponse buscarMotoMaisAntigaPorTipoEPatio(final TipoMoto tipoMoto, final Long patioId) {
        patioExiste(patioId);

        final Moto moto = motoRepository.findOldestMotoByTipoAndPatioId(tipoMoto, patioId)
                .orElseThrow(() -> new MotoNotFoundException("Nenhuma moto do tipo '" + tipoMoto + "' encontrada no pátio " + patioId));

        return construirPosicaoMotoResponse(moto);
    }

    private PosicaoMotoResponse construirPosicaoMotoResponse(Moto moto) {
        Patio patio = null;
        if (moto.getPatioId() != null) {
            patio = patioRepository.findById(moto.getPatioId()).orElse(null);
        }

        final String setor = moto.getSetorPatio() != null ? moto.getSetorPatio().getSetor() : null;

        return new PosicaoMotoResponse(
                moto.getPlaca(),
                moto.getTipoMoto(),
                moto.getStatusMoto(),
                moto.getAno(),
                moto.getPrecoAluguel(),
                patio != null ? patio.getId() : null,
                patio != null ? patio.getApelido() : null,
                patio != null ? patio.getEndereco() : null,
                setor,
                moto.getCodRastreador(),
                moto.getDataEntrada()
        );
    }

    private void alocarNoSetor(final CadastroMotoDto dto, final Long patioId) {
        SetorPatio setorPatio = setorExisite(dto.setor(), patioId);

        boolean excedido = setorPatioRepository.setorComCapacidadeExcedida(patioId, dto.setor());

        if (excedido) {
            throw new SetorCheioException(dto.setor());
        }

        final Moto moto = saveMoto(dto);
        moto.setSetorPatio(setorPatio);
        moto.setPatioId(patioId);
        motoRepository.save(moto);
        setorPatio.getMotos().add(moto);
        setorPatioRepository.save(setorPatio);
    }

    private Moto saveMoto(final CadastroMotoDto motoDto) {
        var moto = Moto.builder()
                .tipoMoto(motoDto.tipoMoto())
                .ano(motoDto.ano())
                .placa(motoDto.placa())
                .precoAluguel(motoDto.precoAluguel())
                .statusMoto(StatusMoto.DISPONIVEL)
                .dataAluguel(null)
                .codRastreador(motoDto.codRastreador())
                .dataEntrada(motoDto.dataEntrada())
                .build();

        return motoRepository.save(moto);
    }

    private void removerDoSetor(Moto moto) {
        SetorPatio setorPatio = moto.getSetorPatio();
        if (setorPatio != null) {
            setorPatio.getMotos().remove(moto);
            setorPatioRepository.save(setorPatio);
        }
        moto.setSetorPatio(null);
    }

    private Moto buscarMotoOrException(final String placa, final String codRastreador) {
        return motoRepository.findByPlacaOrCodRastreador(placa, codRastreador)
                .orElseThrow(() -> new MotoNotFoundException("Moto não encontrada"));
    }

    private SetorPatio setorExisite(final String setor, final Long patioId) {
        patioExiste(patioId);
        return setorPatioRepository.findSetor(setor, patioId)
                .orElseThrow(() -> new SetorNaoExisteException(setor));
    }

    private void patioExiste(final Long patioId) {
        patioRepository.findById(patioId).orElseThrow(() -> new PatioNotFoundException(patioId));
    }

}
