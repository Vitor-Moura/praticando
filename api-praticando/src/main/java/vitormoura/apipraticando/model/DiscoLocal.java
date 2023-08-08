package vitormoura.apipraticando.model;

public class DiscoLocal {

    private String diretorioRaiz;
    private String diretorioDoArquivo;

    public DiscoLocal(String diretorioRaiz, String diretorioDoArquivo) {
        this.diretorioRaiz = diretorioRaiz;
        this.diretorioDoArquivo = diretorioDoArquivo;
    }

    public String getDiretorioRaiz() {
        return diretorioRaiz;
    }

    public void setDiretorioRaiz(String diretorioRaiz) {
        this.diretorioRaiz = diretorioRaiz;
    }

    public String getDiretorioDoArquivo() {
        return diretorioDoArquivo;
    }

    public void setDiretorioDoArquivo(String diretorioDoArquivo) {
        this.diretorioDoArquivo = diretorioDoArquivo;
    }

}


