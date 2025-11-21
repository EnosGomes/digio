package org.example.msdigio.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.msdigio.client.ClientesComprasClient;
import org.example.msdigio.client.ProdutosClient;
import org.example.msdigio.dto.*;
import org.example.msdigio.exception.NaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Comparator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComprasServiceImpl implements ComprasService {

    private final ProdutosClient produtosClient;
    private final ClientesComprasClient clientesClient;

    @Override
    public Page<CompraDetalhadaDTO> listarComprasPageada(
            String cliente,
            String cpf,
            String produto,
            Integer ano,
            Pageable pageable) {

        var produtosMap = produtosClient.buscarProdutos().stream()
                .collect(Collectors.toMap(ProdutoDTO::getCodigo, Function.identity()));

        // Monta lista completa + filtros
        List<CompraDetalhadaDTO> listaFiltrada = clientesClient.buscarClientesECompras().stream()
                .flatMap(cli -> cli.getCompras().stream().map(c -> {
                    var prod = produtosMap.get(c.getCodigo());
                    return CompraDetalhadaDTO.builder()
                            .clienteNome(cli.getNome())
                            .clienteCpf(cli.getCpf())
                            .produtoTipo(prod.getTipo())
                            .safra(prod.getSafra())
                            .anoCompra(prod.getAnoCompra())
                            .quantidade(c.getQuantidade())
                            .precoUnitario(prod.getPreco())
                            .valorTotal(prod.getPreco()
                                    .multiply(BigDecimal.valueOf(c.getQuantidade())))
                            .build();
                }))
                .filter(c -> cliente == null || c.getClienteNome().equalsIgnoreCase(cliente))
                .filter(c -> cpf == null || c.getClienteCpf().equals(cpf))
                .filter(c -> produto == null || c.getProdutoTipo().equalsIgnoreCase(produto))
                .filter(c -> ano == null || c.getAnoCompra().equals(ano.toString()))
                .toList();

        // PAGINAÇÃO MANUAL
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), listaFiltrada.size());

        List<CompraDetalhadaDTO> pageContent =
                (start >= end) ? List.of() : listaFiltrada.subList(start, end);

        return new PageImpl<>(pageContent, pageable, listaFiltrada.size());
    }

    @Override
    public List<CompraDetalhadaDTO> listarCompras() {

        var produtosMap = produtosClient.buscarProdutos().stream()
                .collect(Collectors.toMap(ProdutoDTO::getCodigo, Function.identity()));

        return clientesClient.buscarClientesECompras().stream()
                .flatMap(cli -> cli.getCompras().stream().map(c -> {
                    var prod = produtosMap.get(c.getCodigo());
                    return CompraDetalhadaDTO.builder()
                            .clienteNome(cli.getNome())
                            .clienteCpf(cli.getCpf())
                            .produtoTipo(prod.getTipo())
                            .safra(prod.getSafra())
                            .anoCompra(prod.getAnoCompra())
                            .quantidade(c.getQuantidade())
                            .precoUnitario(prod.getPreco())
                            .valorTotal(prod.getPreco()
                                    .multiply(BigDecimal.valueOf(c.getQuantidade())))
                            .build();
                }))
                .sorted(Comparator.comparing(CompraDetalhadaDTO::getValorTotal))
                .toList();
    }

    @Override
    public MaiorCompraDTO maiorCompraPorAno(Integer ano) {
        return listarCompras().stream()
                .filter(c -> c.getAnoCompra().startsWith(ano.toString()))
                .max(Comparator.comparing(CompraDetalhadaDTO::getValorTotal))
                .map(c -> MaiorCompraDTO.builder()
                        .ano(ano)
                        .clienteNome(c.getClienteNome())
                        .clienteCpf(c.getClienteCpf())
                        .produtoNome(c.getProdutoTipo())
                        .quantidade(c.getQuantidade())
                        .valorTotal(c.getValorTotal())
                        .build())
                .orElseThrow(() -> new NaoEncontradoException("Nenhuma compra encontrada no ano " + ano));
    }

    @Override
    public List<ClienteFielDTO> clientesFieis() {

        return listarCompras().stream()
                .collect(Collectors.groupingBy(CompraDetalhadaDTO::getClienteCpf))
                .entrySet().stream()
                .map(e -> ClienteFielDTO.builder()
                        .cpf(e.getKey())
                        .nome(e.getValue().get(0).getClienteNome())
                        .totalCompras(e.getValue().size())
                        .valorAcumulado(
                                e.getValue().stream()
                                        .map(CompraDetalhadaDTO::getValorTotal)
                                        .filter(Objects::nonNull)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        )
                        .build())
                .sorted(Comparator.comparing(ClienteFielDTO::getValorAcumulado).reversed())
                .limit(3)
                .toList();
    }

    @Override
    public RecomendacaoDTO recomendacao(String cpf) {
        var comprasCliente = listarCompras().stream()
                .filter(c -> c.getClienteCpf().equals(cpf))
                .toList();

        if (comprasCliente.isEmpty())
            throw new NaoEncontradoException("Cliente não encontrado ou sem compras");

        var maisComprado = comprasCliente.stream()
                .collect(Collectors.groupingBy(
                        CompraDetalhadaDTO::getProdutoTipo,
                        Collectors.summingInt(CompraDetalhadaDTO::getQuantidade)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        var cliente = comprasCliente.get(0);

        return RecomendacaoDTO.builder()
                .clienteNome(cliente.getClienteNome())
                .clienteCpf(cliente.getClienteCpf())
                .tipoRecomendado(maisComprado.getKey())
                .motivo("Baseado nas compras recorrentes do tipo " + maisComprado.getKey())
                .build();
    }
}

