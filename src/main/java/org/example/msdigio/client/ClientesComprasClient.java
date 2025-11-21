package org.example.msdigio.client;

import org.example.msdigio.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "clientesComprasClient", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ClientesComprasClient {

    @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
    List<ClienteDTO> buscarClientesECompras();
}
