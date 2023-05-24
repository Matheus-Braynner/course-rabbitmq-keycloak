package io.github.matheusbraynner.msavaliadorcredito.domain.model.feign;

import lombok.Data;

@Data
public class DadosCliente {

    private Long id;
    private String nome;
    private Integer idade;
}
