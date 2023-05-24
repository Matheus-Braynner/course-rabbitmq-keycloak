package io.github.matheusbraynner.mscartoes.application;


import io.github.matheusbraynner.mscartoes.domain.Cartao;
import io.github.matheusbraynner.mscartoes.domain.ClienteCartao;
import io.github.matheusbraynner.mscartoes.dto.CartaoSaveRequest;
import io.github.matheusbraynner.mscartoes.dto.CartoesPorClienteResponse;
import io.github.matheusbraynner.mscartoes.service.CartaoService;
import io.github.matheusbraynner.mscartoes.service.ClienteCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cartoes")
public class CartoesController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam String cpf) {
        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = lista.stream().map(CartoesPorClienteResponse::fromModel).toList();
        return ResponseEntity.ok(resultList);
    }
}
