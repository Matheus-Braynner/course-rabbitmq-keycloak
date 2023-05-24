package io.github.matheusbraynner.mscartoes.service;

import io.github.matheusbraynner.mscartoes.domain.Cartao;
import io.github.matheusbraynner.mscartoes.infra.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository repository;

    public Cartao save(Cartao cartao) {
        return repository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        var rendaBigdecimal = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaBigdecimal);
    }
}
