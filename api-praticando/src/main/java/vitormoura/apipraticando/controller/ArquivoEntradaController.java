package vitormoura.apipraticando.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.model.enums.TipoDeArquivo;
import vitormoura.apipraticando.service.Interface.IArquivoEntradaService;


@RestController
@RequestMapping("/arquivos")
public class ArquivoEntradaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoEntradaController.class);

    @Autowired
    IArquivoEntradaService iArquivoEntradaService;


    @PostMapping("/processarArquivoEntrada")
    public ResponseEntity<String> processarArquivoEntrada(@RequestParam MultipartFile arquivo)  {

        String nomeArquivo = arquivo.getOriginalFilename();
        String descricaoErro = " ";
        boolean sucesso = false;

        if (nomeArquivo.substring(0,20).equalsIgnoreCase(TipoDeArquivo.PAGAMENTOS_PENDENTES.getNome())) {
            //UPLOAD
            if (!iArquivoEntradaService.uploadArquivoPagamentosPendentes(arquivo)) {
                descricaoErro = "Error ao fazer o upload do arquivo " + nomeArquivo;
            }
            //LEITURA
            if (!iArquivoEntradaService.leArquivoPagamentosEmAberto(nomeArquivo)) {
                descricaoErro = "Error ao ler o arquivo" + nomeArquivo;
            }
            //SALVAR REGISTROS
            if (!iArquivoEntradaService.salvarRegistrosPagamentosEmAberto(nomeArquivo)) {
                descricaoErro = "Error ao salvar os registros do arquivo " + nomeArquivo + " no banco de dados.";
            }
            sucesso = true;
        }
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Arquivo inválido");

        return sucesso
                ? ResponseEntity.ok("Arquivo " + nomeArquivo + " processado. Dados gravados no banco de dados.")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(descricaoErro);
    }
}
