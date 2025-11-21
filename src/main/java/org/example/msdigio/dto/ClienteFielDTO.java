package org.example.msdigio.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClienteFielDTO {
    private String nome;
    private String cpf;
    private Integer totalCompras;
    private BigDecimal valorAcumulado;
}

