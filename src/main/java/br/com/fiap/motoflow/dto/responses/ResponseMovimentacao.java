package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ResponseMovimentacao {

    private String placa;
    private TipoMoto tipoMoto;
    private int ano;
    private BigDecimal precoAluguel;
    private StatusMoto statusMoto;
    private int codRastreador;
    private LocalDateTime dataEntrada;
    private String setor;
}
