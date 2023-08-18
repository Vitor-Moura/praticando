package vitormoura.apipraticando.domain.enums;



public enum TipoDeArquivoEntrada {

    PAGAMENTOS_PENDENTES("C:/home","PagamentosPendentes","Pagamentos_Pendentes",".txt",  "ArquivoEntradaPagamentosPendentesImpl"),
    PAGAMENTOS_TESTE("C:/home","PagamentosPendentes","Pagamentos_Pendentes",".txt",  "ArquivoEntradaPagamentosPendentesImpl"),
    PAGAMENTOS_EFETUADOS("C:/home","PagamentosEfetuados","Pagamentos_Efetuados",".csv", "ArquivoSaidaService");

    public final String diretorioRaiz;
    public final String diretorio;
    public final String nome;
    public final String extensao;
    public final String classeImpl;


    TipoDeArquivoEntrada(String diretorioRaiz, String diretorio, String nome, String extensao, String classeImpl) {
        this.diretorioRaiz = diretorioRaiz;
        this.diretorio = diretorio;
        this.nome = nome;
        this.extensao = extensao;
        this.classeImpl = classeImpl;
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

    public String getClasseImpl() {
        return classeImpl;
    }
}
