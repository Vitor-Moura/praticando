package vitormoura.apipraticando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitormoura.apipraticando.model.Email;
import vitormoura.apipraticando.model.Pagamento;
import vitormoura.apipraticando.model.repository.PagamentoRepository;
import vitormoura.apipraticando.service.Interface.IEmailService;
import vitormoura.apipraticando.service.Interface.IPagamentoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    IPagamentoService iPagamentoService;

    @Autowired
    IEmailService iEmailService;


    @PostMapping("/processaArquivoPagamentos")
    public ResponseEntity<String> processaArquivoPagamentos() {
        List<Pagamento> listaArquivoLido = new ArrayList<>();
        String caminhoArquivo = "C:\\Users\\vitor\\Documents\\projetos-java\\api-praticando-git\\PagamentosEmAberto.txt";

        if (!iPagamentoService.leArquivoPagamentosEmAberto(caminhoArquivo)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error ao ler o arquivo.");
        }

        listaArquivoLido = iPagamentoService.getListaPagamentosDoArquivo();
        for (Pagamento pagamento : listaArquivoLido){
            pagamentoRepository.save(pagamento);
        }

        return listaArquivoLido.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok("Arquivo processado. Dados gravados no banco de dados.");
    }

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<Pagamento>pagar(@PathVariable Integer id) {
        if (pagamentoRepository.existsById(id)) {
            Pagamento pagamento = pagamentoRepository.findById(id).get();
            pagamento.setStatusPagamento(true);
            pagamentoRepository.save(pagamento);
            return ResponseEntity.status(HttpStatus.OK).body(pagamento);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/geraEnviaRelatorio")
    public ResponseEntity<String> geraRelatorioPagamentosEfetuados() {
        List<Pagamento> pagamentosEfetuados = pagamentoRepository.findByStatusPagamentoTrue();
        String caminhoArquivo = "C:\\Users\\vitor\\Documents\\projetos-java\\api-praticando-git\\Relatório de Pagamentos Efetuados.csv";
        String nomeArquivo = "Relatório de Pagamentos Efetuados.csv";

        if (!iPagamentoService.gravaArquivoPagamentosEfetuados(pagamentosEfetuados, caminhoArquivo)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error ao gerar relatório.");
        }

        Email email = new Email();
        email.setPara("vitormoura@me.com");
        email.setAssunto("Relatório de pagamentos efetuados");
        email.setCorpo("Segue em anexo o relatório com todos os pagamentos efetuados");
        email.setCaminhoDoArquivo(caminhoArquivo);
        email.setNomeDoArquivo(nomeArquivo);
        email.setHtmlMsg(false);

        return iEmailService.enviaEmail(email)
                ? ResponseEntity.ok("Arquivo gerado e enviado via e-mail.")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar e-mail.");
    }

    @PostMapping("/postPagamento")
    public ResponseEntity<Pagamento> postPagamento(@RequestBody Pagamento pagamento) {
        if (pagamento != null) {
            pagamentoRepository.save(pagamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/getPagamentos")
    public ResponseEntity<List<Pagamento>> getPagamentos() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return pagamentos.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(pagamentos);
    }
}
