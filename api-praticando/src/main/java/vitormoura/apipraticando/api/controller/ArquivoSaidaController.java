package vitormoura.apipraticando.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitormoura.apipraticando.domain.enums.TipoDeArquivo;
import vitormoura.apipraticando.service.IArquivoSaidaService;

@RestController
@RequestMapping("/relatorios")
public class ArquivoSaidaController {
    @Autowired
    IArquivoSaidaService iArquivoSaidaService;

    @GetMapping("/gerarEnviarRelatorio")
    public ResponseEntity<String> gerarEnviarRelatorio(String tipo, String enderecoEnvio) {
        boolean gerarRelario = false;
        boolean enviarRelatorio = false;

        if (tipo.equalsIgnoreCase(TipoDeArquivo.PAGAMENTOS_EFETUADOS.getNome())) {
            gerarRelario = iArquivoSaidaService.gerarRelatorioPagamentosEfetuados();
            if (!gerarRelario) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o relatório");
            }
            enviarRelatorio = iArquivoSaidaService.enviarRelatorioPagamentosEfetuados(enderecoEnvio);

            return enviarRelatorio
                    ? ResponseEntity.ok("Arquivo gerado e enviado via e-mail.")
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Relatório gerado. Erro ao enviar e-mail.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de relatório enválido");
    }
}
