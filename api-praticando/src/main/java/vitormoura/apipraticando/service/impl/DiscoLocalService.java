package vitormoura.apipraticando.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.service.models.DiscoLocal;
import vitormoura.apipraticando.service.IDiscoLocalService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DiscoLocalService implements IDiscoLocalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoLocalService.class);


    public Path criarDiretorio(DiscoLocal discoLocal) throws IOException {
        LOGGER.info("Iniciando a criação do diretório");

            Path caminhoDoDiretorio = Paths.get(discoLocal.getDiretorioRaiz(), discoLocal.getDiretorioDoArquivo());
            LOGGER.info("Caminho do diretorio antes de criar: " + caminhoDoDiretorio);

            Files.createDirectories(caminhoDoDiretorio);

            LOGGER.info("Diretório criado com sucesso em: " + caminhoDoDiretorio);
            return caminhoDoDiretorio;
    }

    public void salvarNoDiscoLocal(MultipartFile arquivo, Path caminhoDoDiretorio) throws IOException {
        Path caminhoDoArquivo = caminhoDoDiretorio.resolve(arquivo.getOriginalFilename());
            arquivo.transferTo(caminhoDoArquivo.toFile());
            LOGGER.info("Arquivo salvo com sucesso em " + String.valueOf(caminhoDoArquivo));
    }
}
