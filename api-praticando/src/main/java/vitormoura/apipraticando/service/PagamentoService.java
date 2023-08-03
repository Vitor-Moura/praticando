package vitormoura.apipraticando.service;

import org.springframework.stereotype.Service;
import vitormoura.apipraticando.model.Pagamento;
import vitormoura.apipraticando.service.Interface.IPagamentoService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

@Service
public class PagamentoService implements IPagamentoService {

    private List<Pagamento> listaPagamentosDoArquivo;

    private List<Pagamento> listaPagamentosEfetuados;


    @Override
    public boolean leArquivoPagamentosEmAberto(String caminhoArquivo) {
        BufferedReader entrada = null;
        String registro, tiporegistro, nomeCred, chavePix, digitoContaCred;
        int agenciaCred, contaCred, qtdRegistroArquivo, contadorRegistroLido = 0;
        long cnpjCpfCred;
        double valor;

        listaPagamentosDoArquivo = new ArrayList<>();

        //abrindo arquivo
        try {
            entrada = new BufferedReader(new FileReader(caminhoArquivo));
        }
        catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        //lendo arquivo
        try {
            registro = entrada.readLine();

            while (registro != null) {
                tiporegistro = registro.substring(0,2);

                if (tiporegistro.equals("00")) {
                    System.out.println("HEADER");
                    System.out.println("Tipo de arquivo: " + registro.substring(2,32));
                    System.out.println("Data/hora da geração do arquivo: " + registro.substring(32,51));
                    System.out.println("Versão do layout: " + registro.substring(51,53));
                } else if (tiporegistro.equals("02")) {
                    nomeCred = registro.substring(2,52).trim();
                    cnpjCpfCred = Long.valueOf(registro.substring(52,66).trim());
                    agenciaCred = Integer.valueOf(registro.substring(66,71).trim());
                    contaCred = Integer.valueOf(registro.substring(71,91).trim());
                    digitoContaCred = registro.substring(91,92);
                    chavePix = registro.substring(92,132).trim();
                    valor = Double.valueOf(registro.substring(132,142).replace(',', '.'));

                    Pagamento pagamento = new Pagamento(nomeCred, cnpjCpfCred, agenciaCred,
                            contaCred, digitoContaCred, chavePix, valor);

                    listaPagamentosDoArquivo.add(pagamento);
                    System.out.println(pagamento);

                } else if (tiporegistro.equals("01")) {
                    System.out.println("TRAILER");
                    qtdRegistroArquivo = Integer.parseInt(registro.substring(2,7));
                    System.out.println("Quantidade de regiostros nos arquivo: " + qtdRegistroArquivo);
                    System.out.println("Quantidade de registros lidos: " + contadorRegistroLido);
                    if (qtdRegistroArquivo != contadorRegistroLido) {
                        System.out.println("Quantidade de registros no arquivo " +
                                "é incompatível com a quantidade de registros lidos");
                    }
                }
                else {
                    System.out.println("Tipo de registro inválido");
                }
                registro = entrada.readLine();
            }
            entrada.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Erro ao ler o arquivo" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean gravaArquivoPagamentosEfetuados(List<Pagamento> listaPagamentosEfetuados, String caminhoArquivo) {
        FileWriter arq = null;
        Formatter saida = null;

        //abrindo arquivo
        try {
            arq = new FileWriter(caminhoArquivo);
            saida = new Formatter(arq);
        }
        catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo" + e.getMessage());
            e.printStackTrace();
            return false;
        }

        //gravando arquivo
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
            System.out.println("Erro ao gravar o relatódio de pagamentos efetuados" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            saida.close();
            try {
                arq.close();
                return true;
            }
            catch (IOException e) {
                System.out.println("Erro ao fechar o arquivo" + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public List<Pagamento> getListaPagamentosDoArquivo() {
        return listaPagamentosDoArquivo;
    }

    public List<Pagamento> getListaPagamentosEfetuados() {
        return listaPagamentosEfetuados;
    }
}
