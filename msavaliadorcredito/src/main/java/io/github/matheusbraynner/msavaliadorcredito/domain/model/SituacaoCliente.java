package io.github.matheusbraynner.msavaliadorcredito.domain.model;

import io.github.matheusbraynner.msavaliadorcredito.domain.model.feign.CartaoCliente;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.feign.DadosCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SituacaoCliente {

    private DadosCliente cliente;
    private List<CartaoCliente> cartoes;
}
