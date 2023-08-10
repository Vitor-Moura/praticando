package vitormoura.apipraticando.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.service.exception.UpdaloadArquivoException;

@Component
public interface IArquivoEntradaService {

    public void processarArquivoPagamentosPendentes (MultipartFile arquivo);

}
