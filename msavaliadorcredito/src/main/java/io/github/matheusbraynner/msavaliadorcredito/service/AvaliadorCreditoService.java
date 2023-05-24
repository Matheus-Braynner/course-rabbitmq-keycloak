package io.github.matheusbraynner.msavaliadorcredito.service;

import feign.FeignException;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.CartaoAprovado;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.feign.Cartao;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.feign.CartaoCliente;
import io.github.matheusbraynner.msavaliadorcredito.domain.model.feign.DadosCliente;
import io.github.matheusbraynner.msavaliadorcredito.ex.DadosClienteNotFoundException;
import io.github.matheusbraynner.msavaliadorcredito.ex.ErroComunicacaoMicroservicesException;
import io.github.matheusbraynner.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.matheusbraynner.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.apache.el.lang.ELArithmetic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliadorCreditoService {

    @Autowired
    private ClienteResourceClient clientesClient;

    @Autowired
    private CartoesResourceClient cartoesClient;

//    @Autowired
//    private AvaliadorCreditoRepository repository;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(dadosCartaoResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getLocalizedMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();


                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeiraCartao(cartao.getBandeiraCartao());
                aprovado.setLimiteAprovado(limiteAprovado);
                return aprovado;
            }).collect(Collectors.toList());
            return new RetornoAvaliacaoCliente(listaCartoesAprovados);
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getLocalizedMessage(), status);
        }
    }
}
