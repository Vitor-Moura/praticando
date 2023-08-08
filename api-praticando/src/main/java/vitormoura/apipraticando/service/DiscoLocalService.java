package vitormoura.apipraticando.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.model.DiscoLocal;
import vitormoura.apipraticando.service.Interface.IDiscoLocalService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DiscoLocalService implements IDiscoLocalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoLocalService.class);


    public Path criarDiretorio(DiscoLocal discoLocal) {
        LOGGER.info("Iniciando a criação do diretório");

            Path caminhoDoDiretorio = Paths.get(discoLocal.getDiretorioRaiz(), discoLocal.getDiretorioDoArquivo());

            try {
                Files.createDirectories(caminhoDoDiretorio);
            } catch (IOException e) {
                LOGGER.error("Erro ao criar diretório " + e.getMessage());
            }
            LOGGER.info("Diretório craido com sucesso.");
            return caminhoDoDiretorio;
    }

    public boolean salvarNoDiscoLocal(MultipartFile arquivo, Path caminhoDoDiretorio){
        Path caminhoDoArquivo = caminhoDoDiretorio.resolve(arquivo.getOriginalFilename());

        try {
            arquivo.transferTo(caminhoDoArquivo.toFile());
            LOGGER.info("Arquivo salvo com sucesso");
            return true;
        }
        catch (IOException e) {
            LOGGER.error("Erro ao salvar o arquivo");
            return false;
        }
    }
}
