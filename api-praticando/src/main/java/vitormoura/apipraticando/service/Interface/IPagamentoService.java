package vitormoura.apipraticando.service.Interface;

import vitormoura.apipraticando.model.Pagamento;

import java.util.List;

public interface IPagamentoService {

    boolean leArquivoPagamentosEmAberto(String caminhoArquivo);

    boolean gravaArquivoPagamentosEfetuados(List<Pagamento> listaPagamentosEfetuados, String caminhoArquivo);

    List<Pagamento> getListaPagamentosDoArquivo();
}
