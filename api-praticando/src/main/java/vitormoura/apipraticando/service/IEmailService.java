package vitormoura.apipraticando.service;

import vitormoura.apipraticando.service.models.Email;

public interface IEmailService {

    boolean enviaEmail(Email email);

}
