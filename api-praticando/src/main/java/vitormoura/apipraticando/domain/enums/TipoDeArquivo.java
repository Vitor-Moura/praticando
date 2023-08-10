package vitormoura.apipraticando.domain.enums;

import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.service.IArquivoEntradaService;


public enum TipoDeArquivo {

    PAGAMENTOS_PENDENTES("C:/home", "Pagamentos Pendentes", "Pagamentos_Pendentes", ".txt") {

        private IArquivoEntradaService iArquivoEntradaService;
        @Override
        public void processarPagamentosPendentes(MultipartFile arquivo) {
            iArquivoEntradaService.processarArquivoPagamentosPendentes(arquivo);
        }
    },
    PAGAMENTOS_EFETUADOS("C:/home", "Pagamentos Efetuados", "Pagamentos_Efetuados", ".csv") {

    };

    public final String diretorioRaiz;
    public final String diretorio;
    public final String nome;
    public final String extensao;


    TipoDeArquivo(String diretorioRaiz, String diretorio, String nome, String extensao) {
        this.diretorioRaiz = diretorioRaiz;
        this.diretorio = diretorio;
        this.nome = nome;
        this.extensao = extensao;
    }

    public void processarPagamentosPendentes(MultipartFile arquivo) {
    }

    public String getDiretorioRaiz() {
        return diretorioRaiz;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public String getNome() {
        return nome;
    }

    public String getExtensao() {
        return extensao;
    }
}
