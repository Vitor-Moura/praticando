package vitormoura.apipraticando.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import vitormoura.apipraticando.service.models.DiscoLocal;
import vitormoura.apipraticando.domain.entities.Pagamento;
import vitormoura.apipraticando.domain.enums.TipoDeArquivo;
import vitormoura.apipraticando.domain.repository.PagamentoRepository;
import vitormoura.apipraticando.service.IArquivoEntradaService;
import vitormoura.apipraticando.service.IDiscoLocalService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArquivoEntradaService implements IArquivoEntradaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoEntradaService.class);

    @Autowired
    IDiscoLocalService iDiscoLocalService;
    @Autowired
    PagamentoRepository pagamentoRepository;
    private List<Pagamento> listaPagamentosEmAberto;


    public boolean uploadArquivoPagamentosPendentes(MultipartFile arquivo){
        LOGGER.info("Iniciando o upload do arquivo " + arquivo.getOriginalFilename());

        DiscoLocal discoLocal = new DiscoLocal(
                TipoDeArquivo.PAGAMENTOS_PENDENTES.getDiretorioRaiz(),
                TipoDeArquivo.PAGAMENTOS_PENDENTES.getDiretorio());

        try {
            Path caminhoDoArquivo = iDiscoLocalService.criarDiretorio(discoLocal);

            iDiscoLocalService.salvarNoDiscoLocal(arquivo, caminhoDoArquivo);

            LOGGER.info("Upload do arquivo " + arquivo.getOriginalFilename() + " realizado com sucesso");
            return true;
        }
        catch (IOException | NotFoundException e) {
            LOGGER.error("Erro ao salvar o arquivo no disco local" + e.getMessage());
            //throw new uploadException("oshgosjhg");
            return false;
        }
    }

    @Override
    public boolean leArquivoPagamentosEmAberto(String nomeArquivo) {
        String caminhoArquivo = TipoDeArquivo.PAGAMENTOS_PENDENTES.getDiretorioRaiz() + "/"
                + TipoDeArquivo.PAGAMENTOS_PENDENTES.getDiretorio() + "/" + nomeArquivo;

        BufferedReader entrada = null;
        String registro, tiporegistro, nomeCred, chavePix, digitoContaCred;
        int agenciaCred, contaCred, qtdRegistroArquivo, contadorRegistroLido = 0;
        long cnpjCpfCred;
        double valor, valorTotalArquivo, valorTotalLido = 0;

        listaPagamentosEmAberto = new ArrayList<>();

        //abrindo arquivo
        try {
            entrada = new BufferedReader(new FileReader(caminhoArquivo));
            LOGGER.info("Inciando a abertura do arquivo " + nomeArquivo);
        }
        catch (IOException e) {
            LOGGER.error("Erro ao abrir o arquivo " + nomeArquivo + " " + e.getMessage());
            return false;
        }

        //lendo arquivo
        try {
            registro = entrada.readLine();
            LOGGER.info("Iniciando a leitura do arquivo " + nomeArquivo);

            while (registro != null) {
                tiporegistro = registro.substring(0,2);

                if (tiporegistro.equals("00")) {
                    LOGGER.info("Iniciando a leitura do header.");
                    LOGGER.info("Tipo de arquivo: " + registro.substring(2,32).trim() + " - Data/hora da geração do arquivo:" +
                            " " + registro.substring(32,51));
                } else if (tiporegistro.equals("02")) {
                    LOGGER.debug("Iniciando leitura do registro");
                    nomeCred = registro.substring(2,52).trim();
                    cnpjCpfCred = Long.valueOf(registro.substring(52,66).trim());
                    agenciaCred = Integer.valueOf(registro.substring(66,71).trim());
                    contaCred = Integer.valueOf(registro.substring(71,91).trim());
                    digitoContaCred = registro.substring(91,92);
                    chavePix = registro.substring(92,132).trim();
                    valor = Double.valueOf(registro.substring(132,142).replace(',', '.'));


                    valorTotalLido += valor;

                    Pagamento pagamento = new Pagamento(nomeCred, cnpjCpfCred, agenciaCred,
                            contaCred, digitoContaCred, chavePix, valor, nomeArquivo);

                    contadorRegistroLido++;
                    listaPagamentosEmAberto.add(pagamento);
                    LOGGER.debug("Quantidade de registros lidos: " + contadorRegistroLido);
                    LOGGER.debug("Valor total lido: " + valorTotalLido);

                } else if (tiporegistro.equals("01")) {
                    LOGGER.info("Iniciando leitura do trailer");
                    qtdRegistroArquivo = Integer.parseInt(registro.substring(2,7));
                    valorTotalArquivo = Double.valueOf(registro.substring(7,20).replace(',', '.'));
                    LOGGER.info("Quantidade de registros informado no arquivo: " + qtdRegistroArquivo);
                    LOGGER.info("Valor total informado no arquivo: " + valorTotalArquivo);
                    if (qtdRegistroArquivo != contadorRegistroLido) {
                        LOGGER.error("Quantidade de registros informado no trailer do arquivo é incompatível " +
                                "com a quantidade de registros lidos");
                        return false;
                    }
                    if (valorTotalArquivo != valorTotalLido) {
                        LOGGER.error("Valor total informado no trailer é incompatível " +
                                "com o valor total calculado na leitura do arquivo");
                        return false;
                    }
                }
                else {
                    LOGGER.error("Tipo de registro inválido");
                    return false;
                }
                registro = entrada.readLine();
            }
            entrada.close();
            LOGGER.info("Arquivo válido. Registros lidos com sucesso");
            return true;
        }
        catch (IOException e) {
            LOGGER.error("Erro ao ler o arquivo Pagamentos em Aberto" + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean salvarRegistrosPagamentosEmAberto (String nomeArquivo) {
        LOGGER.info("Iniciando a inserção dos registros no banco de dados");

        try {
            for (Pagamento pagamento : listaPagamentosEmAberto){
                pagamentoRepository.save(pagamento);
            }
            LOGGER.info("Registros inseridos com sucesso no banco de dados");
        }
        catch (Exception e) {
            LOGGER.error("Erro ao inserir os registros do arquivo Pagamentos em Aberto no banco de dados " + e.getMessage());
        }

       return listaPagamentosEmAberto.isEmpty()
               ? false
               : true;
   }

}
