package infrastructure.service;

import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.Mail;
import jakarta.inject.Inject;

public class EmailService {

    @Inject
    Mailer mailer;

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        mailer.send(Mail.withText(destinatario, assunto, corpo));
        System.out.println("E-mail enviado para: " + destinatario);
    }
}
