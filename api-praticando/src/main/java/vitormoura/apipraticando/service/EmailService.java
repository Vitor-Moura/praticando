package vitormoura.apipraticando.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean enviaEmail(Email email) {
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

            FileSystemResource file = new FileSystemResource(new File(email.getCaminhoDoArquivo()));
            messageHelper.addAttachment(email.getNomeDoArquivo(), file);

            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            return false;
        }
    }
}
