package org.example.msdigio.client;

import org.example.msdigio.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "produtosClient", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ProdutosClient {

    @GetMapping("/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
    List<ProdutoDTO> buscarProdutos();

}
