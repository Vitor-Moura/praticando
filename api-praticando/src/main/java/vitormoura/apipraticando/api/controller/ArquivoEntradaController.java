package vitormoura.apipraticando.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.domain.enums.TipoDeArquivoEntrada;
import vitormoura.apipraticando.service.IArquivoEntradaService;


@RestController
@RequestMapping("/arquivos")
public class ArquivoEntradaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoEntradaController.class);

//    @Autowired
//    private IStrategyFactory<TipoDeArquivo, IArquivoEntradaService> implementacoes;

    @Autowired
    private final PluginRegistry<IArquivoEntradaService, TipoDeArquivoEntrada> pluginRegistry;

    @PostMapping("/processarArquivoEntrada")
    public ResponseEntity<String> processarArquivoEntrada(@RequestParam MultipartFile arquivo, TipoDeArquivoEntrada tipoDeArquivoEntrada)  throws Exception{

        //IArquivoEntradaService iArquivoEntradaService = pluginRegistry.getPluginFor(tipoDeArquivoEntrada);
        return ResponseEntity.ok("Arquivo " + arquivo.getOriginalFilename()
                + " processado. Dados gravados no banco de dados.");







//        String classeImpl = tipoDeArquivo.getClasseImpl();
//        IArquivoEntradaService iArquivoEntradaService = (IArquivoEntradaService) Class.forName(
//                "vitormoura/apipraticando/service/impl/" + classeImpl).getDeclaredConstructor().newInstance();
//        iArquivoEntradaService.processaArquivo(arquivo);

//        IArquivoEntradaService iArquivoEntradaService = implementacoes.get(tipoDeArquivo);
//        iArquivoEntradaService.processaArquivo(arquivo);


//        String nomeArquivo = arquivo.getOriginalFilename();

//        if (!nomeArquivo.substring(0,20).equalsIgnoreCase(TipoDeArquivo.PAGAMENTOS_PENDENTES.getNome())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Arquivo inválido");
//
//        }
//        iArquivoEntradaService.processarArquivoPagamentosPendentes(arquivo);
//        return ResponseEntity.ok("Arquivo " + nomeArquivo + " processado. Dados gravados no banco de dados.");

    }
}
