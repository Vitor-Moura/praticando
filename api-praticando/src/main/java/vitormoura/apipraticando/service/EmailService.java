package vitormoura.apipraticando.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vitormoura.apipraticando.model.Email;
import vitormoura.apipraticando.service.Interface.IEmailService;
import java.io.File;

@Service
public class EmailService implements IEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean enviaEmail(Email email) {
        LOGGER.info("Iníciando envio do e-mail");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(email.getPara());
            messageHelper.setSubject(email.getAssunto());

            if (email.isHtmlMsg()) {
                //para mensagens de texto baseadas em html, definir flag como true
                messageHelper.setText(email.getCorpo(), true);
            } else {
                messageHelper.setText(email.getCorpo());
            }

            FileSystemResource file = new FileSystemResource(new File(email.getCaminhoDoAnexo()));
            messageHelper.addAttachment(email.getNomeDoAnexo(), file);

            mailSender.send(mimeMessage);
            LOGGER.info("Email enviado com sucesso");
            return true;
        } catch (MessagingException e) {
            LOGGER.error("Erro ao enviar email: " + e.getMessage());
            return false;
        }
    }
}
