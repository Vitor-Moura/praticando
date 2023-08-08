package vitormoura.apipraticando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitormoura.apipraticando.model.Pagamento;
import vitormoura.apipraticando.model.repository.PagamentoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<Pagamento>pagar(@PathVariable Integer id) {
        if (pagamentoRepository.existsById(id)) {
            Pagamento pagamento = pagamentoRepository.findById(id).get();
            pagamento.setDataHoraPagamento(LocalDateTime.now());
            pagamentoRepository.save(pagamento);
            return ResponseEntity.status(HttpStatus.OK).body(pagamento);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/postPagamento")
    public ResponseEntity<Pagamento> postPagamento(@RequestBody Pagamento pagamento) throws Exception {
        if (pagamento != null) {
            try {
                pagamentoRepository.save(pagamento);
            }
            catch (Exception e) {
                throw new Exception("Erro ao acessar banco de dados " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/getPagamentos")
    public ResponseEntity<List<Pagamento>> getPagamentos() throws Exception {
        List<Pagamento> pagamentos = new ArrayList<>();
        try {
            pagamentos = pagamentoRepository.findAll();
        }
        catch (Exception e) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            throw new Exception("Erro ao acessar banco de dados " + e.getMessage());
        }
        return pagamentos.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.OK).body(pagamentos);
    }
}
