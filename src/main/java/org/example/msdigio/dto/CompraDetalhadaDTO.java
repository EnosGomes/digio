package org.example.msdigio.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CompraDetalhadaDTO {
    private String clienteNome;
    private String clienteCpf;
    private String produtoTipo;
    private String safra;
    private String anoCompra;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;
}
