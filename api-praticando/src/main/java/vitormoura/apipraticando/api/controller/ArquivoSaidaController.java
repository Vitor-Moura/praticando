package vitormoura.apipraticando.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitormoura.apipraticando.domain.enums.TipoDeArquivoEntrada;
import vitormoura.apipraticando.service.IArquivoSaidaService;

@RestController
@RequestMapping("/relatorios")
public class ArquivoSaidaController {
    @Autowired
    IArquivoSaidaService iArquivoSaidaService;

    @GetMapping("/processarRelatorio")
    public ResponseEntity<String> processarRelatorio(String tipo, String enderecoEnvio) {

        if (tipo.equalsIgnoreCase(TipoDeArquivoEntrada.PAGAMENTOS_EFETUADOS.getNome())) {
            iArquivoSaidaService.processarRelatorioPagamentosEfetuados(enderecoEnvio);
            return ResponseEntity.ok("Relatório gerado e enviado via e-mail");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de relatório enválido");
    }
}