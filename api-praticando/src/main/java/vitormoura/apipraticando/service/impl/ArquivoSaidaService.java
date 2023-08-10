package vitormoura.apipraticando.service.impl;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import vitormoura.apipraticando.service.exception.EmailException;
import vitormoura.apipraticando.service.exception.GerarRelatorioException;
import vitormoura.apipraticando.service.models.DiscoLocal;
import vitormoura.apipraticando.domain.enums.TipoDeArquivo;
import vitormoura.apipraticando.service.models.Email;
import vitormoura.apipraticando.domain.entities.Pagamento;
import vitormoura.apipraticando.domain.repository.PagamentoRepository;
import vitormoura.apipraticando.service.IDiscoLocalService;
import vitormoura.apipraticando.service.IEmailService;
import vitormoura.apipraticando.service.IArquivoSaidaService;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

@Service
public class ArquivoSaidaService implements IArquivoSaidaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoSaidaService.class);

    private String nomeCompletoArquivo;

    @Autowired
    IEmailService iEmailService;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    IDiscoLocalService iDiscoLocalService;

    public void processarRelatorioPagamentosEfetuados(String enderecoEnvio) {
        gerarRelatorioPagamentosEfetuados();
        try {
            enviarRelatorioPagamentosEfetuados(enderecoEnvio);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }



    private void gerarRelatorioPagamentosEfetuados() {
        LOGGER.info("Inciando a busca por pagamentos efetuados no banco de dados");

        List<Pagamento> listaPagamentosEfetuados = new ArrayList<>();

        listaPagamentosEfetuados = pagamentoRepository.findByDataHoraPagamentoNotNull();


        if (listaPagamentosEfetuados.isEmpty()) {
            throw new NotFoundException("Não existem registros de pagamentos efetuados");
        }

        DiscoLocal discoLocal = new DiscoLocal(TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorioRaiz(),
                                TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorio());

        String caminhoDiretorio = null;
        try {
            caminhoDiretorio = String.valueOf(iDiscoLocalService.criarDiretorio(discoLocal));
        } catch (IOException e) {
            LOGGER.error(getClass() + " ==> Método: gerarRelatorioPagamentosEfetuados" );
            throw new GerarRelatorioException("Erro ao criar diretório " + e.getMessage());
        }

        Instant instant = Instant.now();
        LocalDateTime localDate = LocalDateTime.ofInstant(instant, ZoneOffset.of("-03:00"));
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("_ddMMyyyy_HHmm");
        String localDateFormatado = localDate.format(formatoData);
        nomeCompletoArquivo = TipoDeArquivo.PAGAMENTOS_EFETUADOS.getNome()  + localDateFormatado +
                                TipoDeArquivo.PAGAMENTOS_EFETUADOS.getExtensao();

        FileWriter arq = null;
        Formatter saida = null;

        //abrindo arquivo
        LOGGER.info("Iniciando a abertura do arquivo");
        try {
            arq = new FileWriter(caminhoDiretorio + "/" + nomeCompletoArquivo);
            saida = new Formatter(arq);
        }
        catch (IOException e) {
            LOGGER.error(getClass() + " ==> Método: gerarRelatorioPagamentosEfetuados" );
            throw new GerarRelatorioException("Erro ao abrir o arquivo: "
                    + nomeCompletoArquivo + " ==> " + e.getMessage());
        }

        //gravando arquivo
        LOGGER.info("Iniciando a gravação do arquivo");
        try {
            for (int i = 0; i < listaPagamentosEfetuados.size(); i++) {
                Pagamento p = listaPagamentosEfetuados.get(i);
                saida.format("%d;%s;%d;%d;%d;%s;%s;%.2f\n",
                        p.getPagamentoId(),
                        p.getNomeCred(),
                        p.getCnpjCpfCred(),
                        p.getAgenciaCred(),
                        p.getContaCred(),
                        p.getDigitoContaCred(),
                        p.getChavePix(),
                        p.getValor());
            }
        }
        catch (FormatterClosedException e) {
            LOGGER.error(getClass() + " ==> Método: gerarRelatorioPagamentosEfetuados" );
            throw new GerarRelatorioException("Erro ao gravar o arquivo: "
                    + nomeCompletoArquivo + " ==> " + e.getMessage());

        }
        finally {
            saida.close();
            try {
                arq.close();
                LOGGER.info(nomeCompletoArquivo + " gravado com sucesso em " + caminhoDiretorio);
            }
            catch (IOException e) {
                LOGGER.error(getClass() + " ==> Método: gerarRelatorioPagamentosEfetuados" );
                throw new GerarRelatorioException("Erro ao fechar o arquivo: "
                        + nomeCompletoArquivo + " ==> " + e.getMessage());
            }
        }
    }


    public void enviarRelatorioPagamentosEfetuados(String enderecoEnvio) throws EmailException {
        String caminhoDiretorio = TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorioRaiz() + "/"
                                + TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorio();

        LOGGER.info("Criando o email a ser enviado no caminho: " + caminhoDiretorio);
        Email email = new Email();
        email.setPara(enderecoEnvio);
        email.setAssunto(nomeCompletoArquivo);
        email.setCorpo("Segue em anexo o relatório com todos os pagamentos efetuados");
        email.setCaminhoDoAnexo(caminhoDiretorio + "/" + nomeCompletoArquivo);
        email.setNomeDoAnexo(nomeCompletoArquivo);
        email.setHtmlMsg(false);

        try {
            iEmailService.enviaEmail(email);
        }
        catch (MessagingException e) {
            LOGGER.error(getClass() + " ==> Método: enviarRelatorioPagamentosEfetuados" );
            throw new EmailException("Erro ao enviar emial com o relatório: "
                    + nomeCompletoArquivo + " ==> " + e.getMessage());
        }
    }
}
