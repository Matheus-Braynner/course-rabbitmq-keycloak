package io.github.matheusbraynner.mscartoes.service;

import io.github.matheusbraynner.mscartoes.domain.ClienteCartao;
import io.github.matheusbraynner.mscartoes.infra.repositories.ClienteCartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCartaoService {

    @Autowired
    private ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
