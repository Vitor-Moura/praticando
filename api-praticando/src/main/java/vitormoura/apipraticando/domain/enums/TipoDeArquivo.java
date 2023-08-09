package vitormoura.apipraticando.domain.enums;

public enum TipoDeArquivo {
    PAGAMENTOS_PENDENTES("/home", "Pagamentos Pendentes", "Pagamentos_Pendentes", ".txt"),
    PAGAMENTOS_EFETUADOS("/home", "Pagamentos Efetuados", "Pagamentos_Efetuados", ".csv");

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
