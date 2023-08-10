package vitormoura.apipraticando.service.exception;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailException extends MessagingException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailException(String mensagem) {
        super(mensagem);
    }
}
