package io.github.matheusbraynner.msavaliadorcredito.ex;

public class DadosClienteNotFoundException extends Exception {
    public DadosClienteNotFoundException () {
        super("Dados do cliente não encotrado para o CPF informado");
    }
}
