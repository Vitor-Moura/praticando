package vitormoura.apipraticando.service.Interface;

import org.springframework.web.multipart.MultipartFile;
import vitormoura.apipraticando.model.DiscoLocal;
import java.nio.file.Path;

public interface IDiscoLocalService {

    public boolean salvarNoDiscoLocal(MultipartFile arquivo, Path caminhoDoDiretorio);

    public Path criarDiretorio(DiscoLocal discoLocal);
}
