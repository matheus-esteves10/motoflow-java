package br.com.fiap.motoflow.dto.responses;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String codRastreador;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataEntrada;

    private String setor;
}
