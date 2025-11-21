package org.example.msdigio.service.impl;


import org.example.msdigio.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComprasService {

    Page<CompraDetalhadaDTO> listarComprasPageada(Pageable pageable);
    List<CompraDetalhadaDTO> listarCompras();
    MaiorCompraDTO maiorCompraPorAno(Integer ano);
    List<ClienteFielDTO> clientesFieis();
    RecomendacaoDTO recomendacao(String cpf);
}

