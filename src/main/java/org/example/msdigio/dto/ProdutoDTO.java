package org.example.msdigio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {
    private Long codigo;
    @JsonProperty("tipo_vinho")
    private String tipo;
    private BigDecimal preco;
    private String safra;
    @JsonProperty("ano_compra")
    private String anoCompra;
}
