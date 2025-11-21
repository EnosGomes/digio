package org.example.msdigio.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MaiorCompraDTO {
    private Integer ano;
    private String clienteNome;
    private String clienteCpf;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal valorTotal;
}
