package vitormoura.apipraticando.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String para;
    private String cc;
    private String assunto;
    private String corpo;
    private String caminhoDoAnexo;
    private String nomeDoAnexo;
    private boolean isHtmlMsg;


    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getCaminhoDoAnexo() {
        return caminhoDoAnexo;
    }

    public void setCaminhoDoAnexo(String caminhoDoAnexo) {
        this.caminhoDoAnexo = caminhoDoAnexo;
    }

    public String getNomeDoAnexo() {
        return nomeDoAnexo;
    }

    public void setNomeDoAnexo(String nomeDoAnexo) {
        this.nomeDoAnexo = nomeDoAnexo;
    }

    public boolean isHtmlMsg() {
        return isHtmlMsg;
    }

    public void setHtmlMsg(boolean htmlMsg) {
        isHtmlMsg = htmlMsg;
    }
}
