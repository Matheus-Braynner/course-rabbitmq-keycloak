package io.github.matheusbraynner.mscartoes.dto;

import io.github.matheusbraynner.mscartoes.domain.BandeiraCartao;
import io.github.matheusbraynner.mscartoes.domain.Cartao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartaoSaveRequest {

    private String nome;
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
