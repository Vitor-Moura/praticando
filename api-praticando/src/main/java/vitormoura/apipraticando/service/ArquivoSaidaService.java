package vitormoura.apipraticando.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import vitormoura.apipraticando.model.DiscoLocal;
import vitormoura.apipraticando.model.enums.TipoDeArquivo;
import vitormoura.apipraticando.model.Email;
import vitormoura.apipraticando.model.Pagamento;
import vitormoura.apipraticando.model.repository.PagamentoRepository;
import vitormoura.apipraticando.service.Interface.IDiscoLocalService;
import vitormoura.apipraticando.service.Interface.IEmailService;
import vitormoura.apipraticando.service.Interface.IArquivoSaidaService;
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


    @Override
    public boolean gerarRelatorioPagamentosEfetuados() {
        LOGGER.info("Inciando a busca por pagamentos efetuados no banco de dados");

        List<Pagamento> listaPagamentosEfetuados = new ArrayList<>();
        try {
            listaPagamentosEfetuados = pagamentoRepository.findByDataHoraPagamentoNotNull();
            if (listaPagamentosEfetuados.isEmpty()) {
                throw new NotFoundException("Não existem registros de pagamentos efetuados");
            }
        }
        catch (Exception e) {
            LOGGER.error("Erro ao buscar dados de pagamentos efetuados no banco de dados");
            return false;
        }

        DiscoLocal discoLocal = new DiscoLocal(TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorioRaiz(),
                                TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorio());
        String caminhoDiretorio = String.valueOf(iDiscoLocalService.criarDiretorio(discoLocal));

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
            arq = new FileWriter(caminhoDiretorio + "\\" + nomeCompletoArquivo);
            saida = new Formatter(arq);
        }
        catch (IOException e) {
            LOGGER.error(("Erro ao abrir o arquivo" + e.getMessage()));
            e.printStackTrace();
            return false;
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
            LOGGER.error("Erro ao gravar o " + nomeCompletoArquivo + " " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            saida.close();
            try {
                arq.close();
                LOGGER.info(nomeCompletoArquivo + " gravado com sucesso");
                return true;
            }
            catch (IOException e) {
                LOGGER.error("Erro ao fechar o arquivo" + nomeCompletoArquivo + " " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean enviarRelatorioPagamentosEfetuados(String enderecoEnvio) {
        String caminhoDiretorio = TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorioRaiz() + "\\"
                                + TipoDeArquivo.PAGAMENTOS_EFETUADOS.getDiretorio();

        LOGGER.info("Criando o email a ser enviado");
        Email email = new Email();
        email.setPara(enderecoEnvio);
        email.setAssunto(nomeCompletoArquivo);
        email.setCorpo("Segue em anexo o relatório com todos os pagamentos efetuados");
        email.setCaminhoDoAnexo(caminhoDiretorio + "\\" + nomeCompletoArquivo);
        email.setNomeDoAnexo(nomeCompletoArquivo);
        email.setHtmlMsg(false);

        return iEmailService.enviaEmail(email)
                ? true
                : false;
    }
}
