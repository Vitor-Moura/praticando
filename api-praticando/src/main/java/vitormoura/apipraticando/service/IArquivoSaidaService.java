package vitormoura.apipraticando.service;

public interface IArquivoSaidaService {

    public boolean enviarRelatorioPagamentosEfetuados(String enderecoEnvio);

    public boolean gerarRelatorioPagamentosEfetuados();
}
