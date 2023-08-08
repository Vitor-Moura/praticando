package vitormoura.apipraticando.service.Interface;

public interface IArquivoSaidaService {

    public boolean enviarRelatorioPagamentosEfetuados(String enderecoEnvio);

    public boolean gerarRelatorioPagamentosEfetuados();
}
