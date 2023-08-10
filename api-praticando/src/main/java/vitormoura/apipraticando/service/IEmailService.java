package vitormoura.apipraticando.service;

import jakarta.mail.MessagingException;
import vitormoura.apipraticando.service.models.Email;

public interface IEmailService {

    void enviaEmail(Email email) throws MessagingException;

}
