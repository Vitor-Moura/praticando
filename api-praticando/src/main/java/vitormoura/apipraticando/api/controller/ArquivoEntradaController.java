package vitormoura.apipraticando.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.domain.enums.TipoDeArquivo;
import vitormoura.apipraticando.service.IArquivoEntradaService;


@RestController
@RequestMapping("/arquivos")
public class ArquivoEntradaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoEntradaController.class);

    @Autowired
    IArquivoEntradaService iArquivoEntradaService;


    @PostMapping("/processarArquivoEntrada")
    public ResponseEntity<String> processarArquivoEntrada(@RequestParam MultipartFile arquivo)  {
        String nomeArquivo = arquivo.getOriginalFilename();

        if (nomeArquivo.substring(0,20).equalsIgnoreCase(TipoDeArquivo.PAGAMENTOS_PENDENTES.getNome())) {
            //TipoDeArquivo.PAGAMENTOS_PENDENTES.processarPagamentosPendentes(arquivo);
            iArquivoEntradaService.processarArquivoPagamentosPendentes(arquivo);
            return ResponseEntity.ok("Arquivo " + nomeArquivo + " processado. Dados gravados no banco de dados.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Arquivo inválido");
    }
}
