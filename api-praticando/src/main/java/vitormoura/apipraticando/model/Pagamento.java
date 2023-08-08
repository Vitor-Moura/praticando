package vitormoura.apipraticando.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
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

    private String nomeArquivo;

    private LocalDateTime dataHoraPagamento;

    public Pagamento(String nomeCred, Long cnpjCpfCred, Integer agenciaCred, Integer contaCred,
                     String digitoContaCred, String chavePix, Double valor, String nomeArquivo) {
        this.nomeCred = nomeCred;
        this.cnpjCpfCred = cnpjCpfCred;
        this.agenciaCred = agenciaCred;
        this.contaCred = contaCred;
        this.digitoContaCred = digitoContaCred;
        this.chavePix = chavePix;
        this.valor = valor;
        this.nomeArquivo = nomeArquivo;
        this.dataHoraPagamento = null;
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

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    public void setDataHoraPagamento(LocalDateTime dataHoraPagamento) {
        this.dataHoraPagamento = dataHoraPagamento;
    }
}
