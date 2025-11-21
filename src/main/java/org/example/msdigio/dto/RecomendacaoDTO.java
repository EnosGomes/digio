package org.example.msdigio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecomendacaoDTO {
    private String clienteNome;
    private String clienteCpf;
    private String tipoRecomendado;
    private String motivo;
}

