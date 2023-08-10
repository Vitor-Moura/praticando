package vitormoura.apipraticando.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import vitormoura.apipraticando.service.exception.*;

import java.util.Date;

@ControllerAdvice
@RestController
public class Handler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> geralExceptions(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Exception", new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UpdaloadArquivoException.class)
    public final ResponseEntity<ExceptionResponse> handleUpdaloadErroException(UpdaloadArquivoException e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Erro ao fazer o upload do arquivo", new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LerArquivoException.class)
    public final ResponseEntity<ExceptionResponse> handleLerArquivoException(LerArquivoException e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Erro ao ler o arquivo", new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SalvarRegistrosException.class)
    public final ResponseEntity<ExceptionResponse> handleSalvarRegistrosException(SalvarRegistrosException e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Erro ao salvar o arquivo", new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GerarRelatorioException.class)
    public final ResponseEntity<ExceptionResponse> handleGerarRelatorioException(GerarRelatorioException e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Erro ao gerar relatório", new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailException.class)
    public final ResponseEntity<ExceptionResponse> handleEmailException(GerarRelatorioException e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("Erro ao enviar relatório via email", new Date(), e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
