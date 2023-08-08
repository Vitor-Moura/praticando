package vitormoura.apipraticando;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiPraticandoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiPraticandoApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Iniciando Api Praticando");
		SpringApplication.run(ApiPraticandoApplication.class, args);
		LOGGER.info("Api Praticando iniciada e pronta para receber requisições");
	}

}
