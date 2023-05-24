package io.github.matheusbraynner.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovado {

    private String cartao;
    private String bandeiraCartao;
    private BigDecimal limiteAprovado;
}
