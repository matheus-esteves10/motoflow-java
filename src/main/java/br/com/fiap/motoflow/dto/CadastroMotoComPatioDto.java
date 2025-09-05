package br.com.fiap.motoflow.dto;

import br.com.fiap.motoflow.model.enums.StatusMoto;
import br.com.fiap.motoflow.model.enums.TipoMoto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastroMotoComPatioDto {
    private TipoMoto tipoMoto;
    private int ano;
    private String placa;
    private BigDecimal precoAluguel;
    private StatusMoto statusMoto = StatusMoto.DISPONIVEL;
    private LocalDate dataAlocacao;
    private Long idPatio;
    private String posicaoHorizontal;
    private int posicaoVertical;
}
