package vitormoura.apipraticando.service.Interface;

import org.springframework.web.multipart.MultipartFile;

public interface IArquivoEntradaService {

    public boolean leArquivoPagamentosEmAberto(String nomeArquivo);

    public boolean uploadArquivoPagamentosPendentes(MultipartFile arquivo);

    public boolean salvarRegistrosPagamentosEmAberto(String nomeArquivo);

}
