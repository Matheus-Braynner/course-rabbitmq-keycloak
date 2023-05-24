package io.github.matheusbraynner.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RetornoAvaliacaoCliente {

    private List<CartaoAprovado> cartoes;
}
