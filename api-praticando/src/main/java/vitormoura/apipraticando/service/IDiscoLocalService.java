package vitormoura.apipraticando.service;

import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.service.models.DiscoLocal;

import java.io.IOException;
import java.nio.file.Path;

public interface IDiscoLocalService {

    public boolean salvarNoDiscoLocal(MultipartFile arquivo, Path caminhoDoDiretorio);

    public Path criarDiretorio(DiscoLocal discoLocal) throws IOException;
}
