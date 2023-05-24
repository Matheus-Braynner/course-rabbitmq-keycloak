package io.github.matheusbraynner.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DadosAvaliacao {

    private String cpf;
    private Long renda;
}
