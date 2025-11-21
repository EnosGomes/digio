package org.example.msdigio.controller;

import lombok.RequiredArgsConstructor;
import org.example.msdigio.dto.ClienteFielDTO;
import org.example.msdigio.dto.CompraDetalhadaDTO;
import org.example.msdigio.dto.MaiorCompraDTO;
import org.example.msdigio.dto.RecomendacaoDTO;
import org.example.msdigio.service.impl.ComprasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ComprasController {

    private final ComprasService comprasService;

    @GetMapping("/compras")
    public ResponseEntity<Page<CompraDetalhadaDTO>> listarCompras(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String produto,
            @RequestParam(required = false) Integer ano,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false, defaultValue = "valorTotal,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size
        );

        Page<CompraDetalhadaDTO> resultado =
                comprasService.listarComprasPageada(cliente, cpf, produto, ano, pageable);

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<MaiorCompraDTO> maiorCompra(@PathVariable Integer ano) {
        return ResponseEntity.ok(comprasService.maiorCompraPorAno(ano));
    }

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<ClienteFielDTO>> clientesFieis() {
        return ResponseEntity.ok(comprasService.clientesFieis());
    }

    @GetMapping("/recomendacao/{cpf}")
    public ResponseEntity<RecomendacaoDTO> recomendacao(@PathVariable String cpf) {
        return ResponseEntity.ok(comprasService.recomendacao(cpf));
    }
}
