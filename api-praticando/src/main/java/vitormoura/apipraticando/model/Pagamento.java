package vitormoura.apipraticando.model;

import jakarta.persistence.*;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer pagamentoId;
    private String nomeCred;
    private Long cnpjCpfCred;
    private Integer agenciaCred;
    private Integer contaCred;
    private String digitoContaCred;
    private String chavePix;
    private Double valor;
    private Boolean statusPagamento;


    public Pagamento() {
    }

    public Pagamento(String nomeCred, Long cnpjCpfCred, Integer agenciaCred, Integer contaCred,
                     String digitoContaCred, String chavePix, Double valor) {
        this.nomeCred = nomeCred;
        this.cnpjCpfCred = cnpjCpfCred;
        this.agenciaCred = agenciaCred;
        this.contaCred = contaCred;
        this.digitoContaCred = digitoContaCred;
        this.chavePix = chavePix;
        this.valor = valor;
        this.statusPagamento = false;
    }

    public Pagamento(Integer pagamentoId, String nomeCred, Long cnpjCpfCred, Integer agenciaCred,
                     Integer contaCred, String digitoContaCred, String chavePix,
                     Double valor, Boolean statusPagamento) {
        this.pagamentoId = pagamentoId;
        this.nomeCred = nomeCred;
        this.cnpjCpfCred = cnpjCpfCred;
        this.agenciaCred = agenciaCred;
        this.contaCred = contaCred;
        this.digitoContaCred = digitoContaCred;
        this.chavePix = chavePix;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
    }

    public Integer getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(Integer pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public String getNomeCred() {
        return nomeCred;
    }

    public void setNomeCred(String nomeCred) {
        this.nomeCred = nomeCred;
    }

    public Long getCnpjCpfCred() {
        return cnpjCpfCred;
    }

    public void setCnpjCpfCred(Long cnpjCpfCred) {
        this.cnpjCpfCred = cnpjCpfCred;
    }

    public Integer getAgenciaCred() {
        return agenciaCred;
    }

    public void setAgenciaCred(Integer agenciaCred) {
        this.agenciaCred = agenciaCred;
    }

    public Integer getContaCred() {
        return contaCred;
    }

    public void setContaCred(Integer contaCred) {
        this.contaCred = contaCred;
    }

    public String getDigitoContaCred() {
        return digitoContaCred;
    }

    public void setDigitoContaCred(String digitoContaCred) {
        this.digitoContaCred = digitoContaCred;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(Boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
